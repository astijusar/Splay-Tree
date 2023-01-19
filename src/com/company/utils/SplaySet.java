package com.company.utils;

import java.util.Iterator;

public class SplaySet<E extends Comparable<E>> extends BstSet<E> {

    public Node<E> add(E element) {
        Node<E> node = super.add(element);
        splay(node);
        return node;
    }

    public void addAll(Set<E> set) {
        for (E temp : set) {
            if (!this.contains(temp)) {
                this.add(temp);
            }
        }
    }

    // bottom-up remove
    public void remove(E element) {
        if (element == null) {
            throw new IllegalArgumentException("Element is null in get(E element)");
        }

        Node<E> node = root;
        Node<E> last = null;
        Node<E> parent;

        while (node != null) {
            int cmp = c.compare(element, node.element);

            if (cmp < 0) {
                last = node;
                node = node.left;
            } else if (cmp > 0) {
                last = node;
                node = node.right;
            } else if (node.left != null && node.right != null) {

                parent = node.parent;
                Node<E> min = getMinimum(node.right);
                node.element = min.element;
                node.right = super.removeRecursive(node.element, node.right);
                splay(parent);
                size--;
                return;
            }
            else {
                if (node.left != null) {
                    parent = node.parent;
                    node = node.left;
                    node.parent = parent;
                    parent.left = node;
                } else {
                    parent = node.parent;
                    node = node.right;
                    if (node == null) {
                        parent.right = null;
                    } else {
                        node.parent = parent;
                        parent.right = node;
                    }
                }
                splay(parent);
                size--;
                return;
            }
        }
        splay(last);
    }
    public void topDownRemove(E element) {
        if (element == null) {
            throw new IllegalArgumentException("Element is null in topDownRemove(E element)");
        }
        SplaySet<E> leftTree = new SplaySet<>();
        SplaySet<E> rightTree = new SplaySet<>();
        Node<E> node = this.get(element, true);
        if (node.element == element) {
            splay(node);
            leftTree.root = root.left;
            rightTree.root = root.right;
            if (rightTree.root != null)
                rightTree.root.parent = null;
            if (leftTree.root != null) {
                leftTree.root.parent = null;
                Node<E> max = super.getMax(leftTree.root);
                leftTree.splay(max);
                leftTree.root.right = rightTree.root;
                if (rightTree.root != null)
                    rightTree.root.parent = leftTree.root;
                root = leftTree.root;
            } else {
                root = rightTree.root;
            }
        } else {
            splay(node);
        }
    }

    public void retainAll(Set<E> set) {
        for (E temp : this) {
            if (!set.contains(temp)) {
                this.remove(temp);
            }
        }
    }

    protected E get(E element) {
        if (element == null) {
            throw new IllegalArgumentException("Element is null in get(E element)");
        }

        Node<E> node = root;
        Node<E> last = null;
        while (node != null) {
            int cmp = c.compare(element, node.element);

            if (cmp < 0) {
                last = node;
                node = node.left;
            } else if (cmp > 0) {
                last = node;
                node = node.right;
            } else {
                splay(node);
                return node.element;
            }
        }
        splay(last);
        return null;
    }

    public boolean contains(E element) {
        if (element == null) {
            throw new IllegalArgumentException("Element is null in contains(E element)");
        }

        return this.get(element) != null;
    }

    public boolean containsAll(Set<E> set) {
        for (E temp : set) {
            if (!this.contains(temp)) {
                return false;
            }
        }
        return true;
    }

    private void leftRotate(Node<E> node) {
        Node<E> parent = node.parent;
        Node<E> left = node.left;
        node.left = parent;
        parent.right = left;
        if (left != null) {
            left.parent = parent;
        }
        Node<E> gp = node.parent.parent;
        parent.parent = node;
        node.parent = gp;

        if (gp == null) {
            root = node;
        } else {
            if (gp.left == parent) {
                gp.left = node;
            } else {
                gp.right = node;
            }
        }
    }

    private void rightRotate(Node<E> node) {
        Node<E> parent = node.parent;
        Node<E> right = node.right;
        node.right = parent;
        parent.left = right;
        if (right != null) {
            right.parent = parent;
        }
        Node<E> gp = parent.parent;
        node.parent = gp;
        parent.parent = node;

        if (gp == null) {
            root = node;
        } else {
            if (gp.left == parent) {
                gp.left = node;
            } else {
                gp.right = node;
            }
        }
    }

    private void splay(Node<E> node) {
        if (node.parent == null) {
            root = node;
            return;
        }

        if (node.parent.parent == null) {
            // zag
            if (node.parent.right == node) {
                leftRotate(node);
                return;
            }
            // zig
            else {
                rightRotate(node);
                return;
            }
        }

        // zag-zig
        if (node.parent.right == node && node.parent.parent.left == node.parent) {
            leftRotate(node);
            rightRotate(node);
            splay(node);
            return;
        }

        // zig-zag
        if (node.parent.left == node && node.parent.parent.right == node.parent) {
            rightRotate(node);
            leftRotate(node);
            splay(node);
            return;
        }

        // zag-zag
        if (node.parent.right == node && node.parent.parent.right == node.parent) {
            leftRotate(node.parent);
            leftRotate(node);
            splay(node);
            return;
        }

        // zig-zig
        if (node.parent.left == node && node.parent.parent.left == node.parent) {
            rightRotate(node.parent);
            rightRotate(node);
            splay(node);
        }
    }
    public void splayValue(E value) {
        splay(get(value, true));
    }

    public void addWithoutSplaying(E element) {
        super.add(element);
    }
}
