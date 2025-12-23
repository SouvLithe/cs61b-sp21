package deque;

import java.util.Iterator;

public class ArrayDeque<T> implements Deque<T>, Iterable<T> {
    private int size;
    private T[] items;
    // 指向队列中第一个（头部）元素在数组中的位置
    private int first;
    // 指向队列中下一个要插入元素的位置
    private int last;
    private double factor;

    public ArrayDeque() {
        size = 0;
        items = (T[]) new Object[8];
        first = 0;
        last = 0;
        factor = 0.5;
    }

    private void resize(int newSize) {
        // 新建一个数组，并把新的大小赋给它
        T[] newArray = (T[]) new Object[newSize];
        int current = 0;
        for (int i = 0; i < size; i++) {
            // i + first 给老数组整理了一遍放入新数组
            newArray[current] = items[(i + first) % items.length];
            current++;
        }
        items = newArray;
        first = 0;
        last = size == 0 ? 0 : current - 1;
    }

    @Override
    public void addFirst(T item) {
        if (size == items.length) {
            resize(size * 2);
        }
        if (size != 0) {
            first = (first + items.length - 1) % items.length;
        }
        items[first] = item;
        size++;
    }

    @Override
    public void addLast(T item) {
        if (size == items.length) {
            resize(size * 2);
        }
        if (size != 0) {
            last = (last + 1) % items.length;
        }
        items[last] = item;
        size += 1;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        for (int i = 0; i < size; i++) {
            System.out.print(items[(first + i) % items.length]);
            System.out.print(" ");
        }
        System.out.println();
    }

    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        T res = items[first];
        items[first] = null;
        if (size != 1) {
            first = (first + 1) % items.length;
        }
        size -= 1;
        if (items.length > 8 && (double) size / items.length < factor) {
            resize(items.length / 2);
        }
        return res;
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        T res = items[last];
        items[last] = null;
        if (size != 1) {
            last = (last + items.length - 1) % items.length;
        }
        size -= 1;
        if (items.length > 8 && (double) size / items.length < factor) {
            resize(items.length / 2);
        }
        return res;
    }

    @Override
    public T get(int index) {
        if (index >= size) {
            return null;
        }
        return items[(first + index) % items.length];
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }

    // 和LinkedListDeque类中的LinkedListDequeIterator一致
    private class ArrayDequeIterator implements Iterator<T> {
        private int pos = 0;

        @Override
        public boolean hasNext() {
            return pos < size;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                return null;
            }
            T res = get(pos);
            pos += 1;
            return res;
        }
    }

    //渐进式设计
    @Override
    public boolean equals(Object o) {
        // 查看引用地址是否相同
        if (this == o) {
            return true;
        }
        // 看看两者类型是否相等
        if (o instanceof Deque) {
            Deque<T> target = (Deque<T>) o;
            //两者大小是否相等
            if (size != target.size()) {
                return false;
            }
            //两者中的每个元素内容是否相等
            for (int i = 0; i < size; i++) {
                if (!target.get(i).equals(this.get(i))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
