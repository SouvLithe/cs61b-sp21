package deque;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T> {
    private Comparator<T> cmp;

    /*
     * 使用给定的比较器创建一个MaxArrayDeque
     * */
    public MaxArrayDeque(Comparator<T> c) {
        // 拿到父类的环境
        super();
        cmp = c;
    }

    /*
     * 返回队列中由先前给定的Comparator控制的最大元素。如果MaxArrayDeque为空，则返回null
     * */
    public T max() {
        if (size() == 0) {
            return null;
        }
        T max = get(0);
        for (T current : this) {
            // 比较的顺序不能反 ，负数小于，0等于，正数大于
            if (cmp.compare(current, max) > 0) {
                max = current;
            }
        }
        return max;
    }

    /*
     * 返回由参数Comparator c控制的队列中的最大元素。如果MaxArrayDeque为空，则返回null
     * */
    public T max(Comparator<T> c) {
        if (size() == 0) {
            return null;
        }
        T max = get(0);
        for (T current : this) {
            if (c.compare(current, max) > 0) {
                max = current;
            }
        }
        return max;
    }
}