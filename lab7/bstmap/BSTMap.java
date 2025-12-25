package bstmap;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    // 内部类，构建节点的数据结构
    private class BSTNode {
        private final K key;
        private final V val;
        private BSTNode left;
        private BSTNode right;

        private BSTNode(K key, V val) {
            this.key = key;
            this.val = val;
            this.left = null;
            this.right = null;
        }

        /**
         * BST的中序遍历（In-order Traversal）方法
         * 中序遍历会按照键值升序排列节点
         */
        private List<BSTNode> nodesInOrder() {
            List<BSTNode> keys = new ArrayList<>();
            // addAll 如果此列表是由于该调用操作而发生变化，则为true。
            if (left != null) {
                keys.addAll(left.nodesInOrder());
            }
            // add 如果此次调用导致该集合发生改变，则为true。
            keys.add(this);
            if (right != null) {
                keys.addAll(right.nodesInOrder());
            }
            return keys;
        }

    }

    // BSTMap 维护两个属性 根节点 和 数据结构大小大小
    private BSTNode root;
    private int size;

    public BSTMap() {
        clear();
    }

    /**
     * 利用赋空值的方式清空 BSTMap
     */
    @Override
    public void clear() {
        this.root = null;
        this.size = 0;
    }

    /**
     * 判断是否存在某个键 key
     */
    @Override
    public boolean containsKey(K key) {
        if (this.root == null) {
            return false;
        }
        Object[] object = bSearch(this.root, key);
        // 如果数组中第3个元素不为0，说明没有找到这个键
        return object[2].equals(0);
    }

    /**
     * 拿到节点的值 val
     */
    @Override
    public V get(K key) {
        if (this.root == null) {
            return null;
        }
        Object[] object = bSearch(this.root, key);
        if (object[2].equals(0)) {
            BSTNode node = (BSTNode) object[1];
            return node.val;
        }
        return null;
    }

    /**
     * 直接利用所维护的 size 返回值
     */
    @Override
    public int size() {
        return this.size;
    }

    /**
     * 利用 bSearch 返回数组的第三个元素判断如何放置节点
     */
    @Override
    public void put(K key, V value) {
        if (this.root == null) {
            this.root = new BSTNode(key, value);
            this.size++;
            return;
        }
        BSTNode node = this.root;
        Object[] object = bSearch(node, key);
        node = (BSTNode) object[1];
        // BSTMap 重复元素不进行添加
        if (object[2].equals(0)) {
            return;
        }
        if (object[2].equals(1)) {
            node.right = new BSTNode(key, value);
        } else {
            node.left = new BSTNode(key, value);
        }
        this.size++;
    }

    /**
     * 利用列表的有序性，将中序遍历后的节点列表放入
     * 遍历得到键
     * 再 Copy 给集合
     */
    @Override
    public Set<K> keySet() {
        if (this.size == 0) {
            return null;
        }
        List<K> keys = new ArrayList<>();
        for (BSTNode bstNode : this.root.nodesInOrder()) {
            keys.add(bstNode.key);
        }
        return Set.copyOf(keys);
    }

    /**
     * 利用 key 删除节点
     */
    @Override
    public V remove(K key) {
        // 1. 搜索要删除的节点
        Object[] obj = bSearch(this.root, key);
        // 父节点,要删除的节点,备份,保存返回值
        BSTNode prev = (BSTNode) obj[0];
        BSTNode node = (BSTNode) obj[1];
        BSTNode rm = node;
        V result = rm.val;

        // 2. 根据不同情况删除节点
        if (node == this.root) {
            removeRoot(node, rm);
        } else if (node.left == null && node.right == null) {
            // 叶子节点：直接删除
            if (obj[2].equals(-1)) {
                prev.left = null;
            } else {
                prev.right = null;
            }
        } else {
            // 非叶子节点：用findRightMostLeft找到替换节点
            node = findRightMostLeft(node, rm);
            if (obj[2].equals(-1)) {
                // 更新父节点的左指针
                prev.left = node;
            } else {
                // 更新父节点的右指针
                prev.right = node;
            }
        }
        this.size--;
        return result;
    }

    /**
     * 给定键值删除节点
     */
    @Override
    public V remove(K key, V value) {
        return remove(key);
    }

    /**
     * 对整理好的keyset 进行迭代
     */
    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }

    public void printInOrder() {
        StringBuilder builder = new StringBuilder();
        for (BSTNode bstNode : this.root.nodesInOrder()) {
            builder.append(bstNode.val.toString());
        }
        System.out.println(builder);
    }
    /**
     * 利用一个给定的 BSTNode 和 一个用于查找的键
     * Returns: 以Object数组形式返回查找结果
     * [0] = prev：找到的节点的父节点（如果找到的话）
     * [1] = node：找到的节点（如果存在）或最后一个访问的节点
     * [2] = int flag：查找状态标志
     */
    private Object[] bSearch(BSTNode node, K key) {
        // 用于记录前一个节点（父节点）
        BSTNode prev = null;
        while (true) {
            if (node.key.compareTo(key) > 0) {
                // 情况 1：如果当前键 > 目标键：向左子树移动
                if (node.left != null) {
                    // 步骤 1：记录当前节点作为父节点
                    prev = node;
                    // 步骤 2：向左子树移动
                    node = node.left;
                } else {
                    // 步骤3：左子树为空，说明目标键不存在
                    return new Object[]{prev, node, -1};
                    // node: 当前节点（没有左子节点）
                    // -1: 表示目标键应该插入到当前节点的左边
                }
            } else if (node.key.compareTo(key) < 0) {
                // 情况 2：如果当前键 < 目标键：向右子树移动,与上面类似
                if (node.right != null) {
                    prev = node;
                    node = node.right;
                } else {
                    return new Object[]{prev, node, 1};
                }
            } else {
                // 情况 1：如果相等：找到了目标节点
                // 为根节点，拿到目标元素
                return new Object[]{prev, node, 0};
            }
        }
    }

    /**
     * 找到并移除左子树中的最右节点（最大节点），用其替换要删除的节点
     *
     * @param node 待删除节点(rm)的左子节点
     * @param rm   要删除的节点
     * @return
     */
    private BSTNode findRightMostLeft(BSTNode node, BSTNode rm) {
        if (node.left == null) {
            // 情况1：左子节点没有左子树,直接提升右子节点
            node = node.right;
        } else if (node.right == null) {
            // 情况2：左子节点没有右子树,直接提升左子节点
            node = node.left;
        } else {
            // 情况3：左子节点有左右子树,移动到左子节点
            node = node.left;
            if (node.right != null) {
                // 寻找左子树中的最右节点（最大节点）
                BSTNode pn = null;
                while (node.right != null) {
                    // 记录父节点
                    pn = node;
                    // 一直向右移动
                    node = node.right;
                }
                // 此时node是左子树中的最右节点
                pn.right = node.left; // 用node的左子节点替换node的位置
                node.left = rm.left;  // 让node接管rm的整个左子树
            }
            node.right = rm.right; // 让node接管rm的右子树
        }
        return node;
    }

    /**
     * 处理删除根节点的特殊情况
     * @param node: 当前处理的节点（起始为根节点）
     * @param rm: 要删除的根节点
     */
    private void removeRoot(BSTNode node, BSTNode rm) {
        if (node.left != null) {
            // 根节点有左子树,用左子树的最大节点作为新根
            BSTNode newNode = findRightMostLeft(node, rm);
            this.root = newNode;
        } else {
            // 根节点没有左子树,直接提升右子树为根
            this.root = this.root.right;
        }
    }

}
