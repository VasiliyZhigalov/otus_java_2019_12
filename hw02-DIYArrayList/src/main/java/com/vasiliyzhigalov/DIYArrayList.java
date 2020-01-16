package com.vasiliyzhigalov;
import java.util.*;

/**
 * Собственная реализация ArrayList на основе массива
 * @param <E>
 */

public class DIYArrayList<E> implements List<E> {
    private static int INIT_SIZE = 10;
    private int pointer = 0;
    private Object[] array = new Object[INIT_SIZE];

    @Override
    public int size() {
        return pointer;
    }

    @Override
    public boolean isEmpty() {
        return pointer == 0;
    }

    @Override
    public boolean contains(Object o) {
        for (int index = 0; index < pointer; index++)
            if (array[index].equals(0))
                return true;
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < pointer && array[currentIndex] != null;
            }

            @Override
            public E next() {
                checkIndex(currentIndex, pointer);
                return (E) array[currentIndex++];
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    @Override
    public Object[] toArray() {
        Object[] result = new Object[pointer];
        System.arraycopy(array, 0, result, 0, pointer);
        return result;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean add(E e) {
        int lastSize = size();
        if (pointer == array.length)
            resize(array.length * 2);
        array[pointer++] = e;
        return isModify(lastSize, size());
    }

    private void resize(int newLength) {
        Object[] newArray = new Object[newLength];
        System.arraycopy(array, 0, newArray, 0, pointer);
        array = newArray;
    }

    private boolean isModify(int lastSize, int newSize) {
        return lastSize != newSize;
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public E get(int index) {
        return (E) array[checkIndex(index, pointer)];
    }

    @Override
    public E set(int index, E element) {
        array[checkIndex(index, pointer)] = element;
        return element;
    }

    @Override
    public void add(int index, E element) {
        int validIndex = checkIndex(index, pointer);
        array[validIndex] = element;
        for (int i = pointer; i >= validIndex; i--)
            array[i + 1] = array[i];
        pointer++;
    }

    @Override
    public E remove(int index) {
        int validIndex = checkIndex(index, pointer);
        for (int i = validIndex; i < pointer; i++)
            array[i] = array[i + 1];
        pointer--;
        return (E) array;
    }

    @Override
    public int indexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override

    public ListIterator<E> listIterator() {
        return new ListIterator<E>() {
            private int currentIndex = 0;
            private int lastIndex = -1;

            @Override
            public boolean hasNext() {
                return currentIndex < pointer && array[currentIndex] != null;
            }

            @Override
            public E next() {
                E result;
                if (!hasNext()) {
                    throw new NoSuchElementException();
                } else {
                    lastIndex = currentIndex;
                    result = get(currentIndex);
                    currentIndex++;
                }
                return result;
            }

            @Override
            public boolean hasPrevious() {
                return currentIndex > 0;
            }

            @Override
            public E previous() {
                if (!hasPrevious()) {
                    throw new NoSuchElementException();
                } else {
                    lastIndex = currentIndex;
                    return (E) array[--currentIndex];
                }
            }

            @Override
            public int nextIndex() {
                return currentIndex;
            }

            @Override
            public int previousIndex() {
                return currentIndex - 1;
            }

            @Override
            public void remove() {

            }

            @Override
            public void set(E e) {
                array[lastIndex] = e;
            }

            @Override
            public void add(E e) {
            }
        };
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        Object[] result = new Object[pointer];
        System.arraycopy(array, 0, result, 0, pointer);
        return Arrays.toString(result);
    }

    private int checkIndex(int index, int length) {
        if (index >= 0 && index < length)
            return index;
        else {
            throw new IndexOutOfBoundsException();
        }
    }

}
