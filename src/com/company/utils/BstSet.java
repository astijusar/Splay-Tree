package com.company.utils;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Stack;

/**
 * Rikiuojamos objektų kolekcijos - aibės realizacija dvejetainiu paieškos
 * medžiu.
 *
 * @param <E> Aibės elemento tipas. Turi tenkinti interfeisą Comparable<E>, arba
 *            per klasės konstruktorių turi būti paduodamas Comparator<E> interfeisą
 *            tenkinantis objektas.
 */
public class BstSet<E extends Comparable<E>> implements SortedSet<E>, Cloneable {

    // Medžio šaknies mazgas
    protected Node<E> root = null;
    // Medžio dydis
    protected int size = 0;
    // Rodyklė į komparatorių
    protected Comparator<? super E> c;

    /**
     * Sukuriamas aibės objektas DP-medžio raktams naudojant Comparable<E>
     */
    public BstSet() {
        this.c = Comparator.naturalOrder();
    }

    /**
     * Sukuriamas aibės objektas DP-medžio raktams naudojant Comparator<E>
     *
     * @param c Komparatorius
     */
    public BstSet(Comparator<? super E> c) {
        this.c = c;
    }

    /**
     * Patikrinama ar aibė tuščia.
     *
     * @return Grąžinama true, jei aibė tuščia.
     */
    @Override
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * @return Grąžinamas aibėje esančių elementų kiekis.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Išvaloma aibė.
     */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Patikrinama ar aibėje egzistuoja elementas.
     *
     * @param element - Aibės elementas.
     * @return Grąžinama true, jei aibėje egzistuoja elementas.
     */
    @Override
    public boolean contains(E element) {
        if (element == null) {
            throw new IllegalArgumentException("Element is null in contains(E element)");
        }

        return get(element) != null;
    }

    /**
     * Patikrinama ar visi abės set elementai egzistuoja aibėje
     *
     * @param set aibė
     * @return
     */
    @Override
    public boolean containsAll(Set<E> set) {
        for (E temp : set) {
            if (!this.contains(temp)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Aibė papildoma nauju elementu.
     *
     * @param element - Aibės elementas.
     */
    @Override
    public Node<E> add(E element) {
        if (root == null) {
            root = new Node<E>(element);
            size++;
            return root;
        }

        Node<E> insertParentNode = null;
        Node<E> searchTempNode = root;
        while (searchTempNode != null && searchTempNode.element != null) {
            insertParentNode = searchTempNode;
            if (element.compareTo(searchTempNode.element) < 0) {
                searchTempNode = searchTempNode.left;
            } else {
                searchTempNode = searchTempNode.right;
            }
        }

        Node<E> newNode = new Node<>(element, insertParentNode);
        if (insertParentNode.element.compareTo(newNode.element) > 0) {
            insertParentNode.left = newNode;
        } else {
            insertParentNode.right = newNode;
        }

        size++;
        return newNode;
    }

    /**
     * Abės set elementai pridedami į esamą aibę, jeigu abi aibės turi tą patį elementą, jis nėra dedamas.
     *
     * @param set pridedamoji aibė
     */
    @Override
    public void addAll(Set<E> set) {
        for (E temp : set) {
            if (!this.contains(temp)) {
                add(temp);
            }
        }
    }

    private Node<E> addRecursive(E element, Node<E> node, Node<E> parent) {

        if (node == null) {
            size++;
            return new Node<>(element, parent);
        }

        int cmp = c.compare(element, node.element);

        if (cmp < 0) {
            node.left = addRecursive(element, node.left, node);
        } else if (cmp > 0) {
            node.right = addRecursive(element, node.right, node);
        }

        return node;
    }

    public Node<E> getMinimum(Node<E> curr) {
        while (curr.left != null) {
            curr = curr.left;
        }
        return curr;
    }

    /**
     * Pašalinamas elementas iš aibės.
     *
     * @param element - Aibės elementas.
     */
    @Override
    public void remove(E element) {

        if (element == null) {
            return;
        }

        root = removeRecursive(element, root);
    }

    /**
     * Aibėje lieka tik tie elementai, kurie yra aibėje set.
     *
     * @param set aibė
     */
    @Override
    public void retainAll(Set<E> set) {
        for (E temp : this) {
            if (!set.contains(temp)) {
                remove(temp);
            }
        }
    }

    protected Node<E> removeRecursive(E element, Node<E> node) {
        if (root == null)
            return root;

        int cmp = c.compare(element, node.element);

        if (cmp < 0) {
            node.left = removeRecursive(element, node.left);
        } else if (cmp > 0) {
            node.right = removeRecursive(element, node.right);
        } else if (node.left != null && node.right != null) {

            Node<E> min = getMinimum(node.right);
            node.element = min.element;
            node.right = removeRecursive(node.element, node.right);
            size--;
        } else {
            if (node.left != null) {
                node = node.left;
            } else {
                node = node.right;
            }
            size--;
        }

        return node;
    }

    protected Node<E> get(E element, boolean tr) {
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
                return node;
            }
        }

        return last;
    }

    protected E get(E element) {
        if (element == null) {
            throw new IllegalArgumentException("Element is null in get(E element)");
        }

        Node<E> node = root;
        while (node != null) {
            int cmp = c.compare(element, node.element);

            if (cmp < 0) {
                node = node.left;
            } else if (cmp > 0) {
                node = node.right;
            } else {
                return node.element;
            }
        }

        return null;
    }

    protected Node<E> get(Node<E> node, boolean findMax) {
        Node<E> parent = null;
        while (node != null) {
            parent = node;
            node = (findMax) ? node.right : node.left;
        }
        return parent;
    }

    Node<E> getMax(Node<E> node) {
        return get(node, true);
    }

    public Node<E> getRoot() {
        return root;
    }

    /**
     * Grąžinamas aibės elementų masyvas.
     *
     * @return Grąžinamas aibės elementų masyvas.
     */
    @Override
    public Object[] toArray() {
        int i = 0;
        Object[] array = new Object[size];
        for (Object o : this) {
            array[i++] = o;
        }
        return array;
    }

    /**
     * Aibės elementų išvedimas į String eilutę Inorder (Vidine) tvarka. Aibės
     * elementai išvedami surikiuoti didėjimo tvarka pagal raktą.
     *
     * @return elementų eilutė
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (E element : this) {
            sb.append(element.toString()).append(System.lineSeparator());
        }
        return sb.toString();
    }

    /**
     * Medžio vaizdavimas simboliais, žiūr.: unicode.org/charts/PDF/U2500.pdf
     * Tai 4 galimi terminaliniai simboliai medžio šakos gale
     */
    private static final String[] term = {"\u2500", "\u2534", "\u252C", "\u253C"};
    private static final String rightEdge = "\u250C";
    private static final String leftEdge = "\u2514";
    private static final String endEdge = "\u25CF";
    private static final String vertical = "\u2502  ";
    private String horizontal;

    /* Papildomas metodas, išvedantis aibės elementus į vieną String eilutę.
     * String eilutė formuojama atliekant elementų postūmį nuo krašto,
     * priklausomai nuo elemento lygio medyje. Galima panaudoti spausdinimui į
     * ekraną ar failą tyrinėjant medžio algoritmų veikimą.
     *
     */
    @Override
    public String toVisualizedString(String dataCodeDelimiter) {
        horizontal = term[0] + term[0];
        return root == null ? ">" + horizontal
                : toTreeDraw(root, ">", "", dataCodeDelimiter);
    }

    private String toTreeDraw(Node<E> node, String edge, String indent, String dataCodeDelimiter) {
        if (node == null) {
            return "";
        }
        String step = (edge.equals(leftEdge)) ? vertical : "   ";
        StringBuilder sb = new StringBuilder();
        sb.append(toTreeDraw(node.right, rightEdge, indent + step, dataCodeDelimiter));
        int t = (node.right != null) ? 1 : 0;
        t = (node.left != null) ? t + 2 : t;
        sb.append(indent).append(edge).append(horizontal).append(term[t]).append(endEdge).append(
                split(node.element.toString(), dataCodeDelimiter)).append(System.lineSeparator());
        step = (edge.equals(rightEdge)) ? vertical : "   ";
        sb.append(toTreeDraw(node.left, leftEdge, indent + step, dataCodeDelimiter));
        return sb.toString();
    }

    private String split(String s, String dataCodeDelimiter) {
        int k = s.indexOf(dataCodeDelimiter);
        if (k <= 0) {
            return s;
        }
        return s.substring(0, k);
    }

    /**
     * Sukuria ir grąžina aibės kopiją.
     *
     * @return Aibės kopija.
     * @throws CloneNotSupportedException
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        BstSet<E> cl = (BstSet<E>) super.clone();
        if (root == null) {
            return cl;
        }
        cl.root = cloneRecursive(root);
        cl.size = this.size;
        return cl;
    }

    private Node<E> cloneRecursive(Node<E> node) {
        if (node == null) {
            return null;
        }

        Node<E> clone = new Node<>(node.element);
        clone.left = cloneRecursive(node.left);
        clone.right = cloneRecursive(node.right);
        return clone;
    }

    /**
     * Grąžinamas aibės poaibis iki elemento.
     *
     * @param element - Aibės elementas.
     * @return Grąžinamas aibės poaibis iki elemento.
     */
    @Override
    public Set<E> headSet(E element) {

        Set<E> temp = new BstSet<E>();
        if (root.element.compareTo(element) > 0) {
            return temp;
        }
        return headSetRecursive(root, temp, element);
    }

    private Set<E> headSetRecursive(Node<E> node, Set<E> temp, E element) {
        if (node == null) {
            return temp;
        }
        if (node.element.compareTo(element) < 0) {
            temp.add(node.element);
        }
        headSetRecursive(node.left, temp, element);
        headSetRecursive(node.right, temp, element);
        return temp;
    }

    /**
     * Grąžinamas aibės poaibis nuo elemento element1 iki element2.
     *
     * @param element1 - pradinis aibės poaibio elementas.
     * @param element2 - galinis aibės poaibio elementas.
     * @return Grąžinamas aibės poaibis nuo elemento element1 iki element2.
     */
    @Override
    public Set<E> subSet(E element1, E element2) {

        Set<E> rez = new BstSet<E>();

        if (element1.compareTo(element2) > 0) {
            E temp = element1;
            element1 = element2;
            element2 = temp;
        }
        if (element1.compareTo(element2) >= 0) {
            return rez;
        }

        return subSetRecursive(root, rez, element1, element2);
    }

    private Set<E> subSetRecursive(Node<E> node, Set<E> temp, E element1, E element2) {
        if (node == null) {
            return temp;
        }
        if ((node.element.compareTo(element1) >= 0) &&
                (node.element.compareTo(element2) < 0)) {
            temp.add(node.element);
        }
        subSetRecursive(node.left, temp, element1, element2);
        subSetRecursive(node.right, temp, element1, element2);
        return temp;
    }

    /**
     * Grąžinamas aibės poaibis nuo elemento.
     *
     * @param element - Aibės elementas.
     * @return Grąžinamas aibės poaibis nuo elemento.
     */
    @Override
    public Set<E> tailSet(E element) {

        Set<E> rez = new BstSet<E>();
        if (root == null) {
            return rez;
        }

        return tailSetRecursive(root, rez, element);
    }

    private Set<E> tailSetRecursive(Node<E> node, Set<E> temp, E element) {
        if (node == null) {
            return temp;
        } else if (node.element.compareTo(element) >= 0) {
            temp.add(node.element);
        }

        tailSetRecursive(node.left, temp, element);
        tailSetRecursive(node.right, temp, element);
        return temp;
    }

    /**
     * Grąžinamas tiesioginis iteratorius.
     *
     * @return Grąžinamas tiesioginis iteratorius.
     */
    @Override
    public Iterator<E> iterator() {
        return new IteratorBst(true);
    }

    /**
     * Grąžinamas atvirkštinis iteratorius.
     *
     * @return Grąžinamas atvirkštinis iteratorius.
     */
    @Override
    public Iterator<E> descendingIterator() {
        return new IteratorBst(false);
    }


    /**
     * Vidinė objektų kolekcijos iteratoriaus klasė. Iteratoriai: didėjantis ir
     * mažėjantis. Kolekcija iteruojama kiekvieną elementą aplankant vieną kartą
     * vidine (angl. inorder) tvarka. Visi aplankyti elementai saugomi steke.
     * Stekas panaudotas iš java.util paketo, bet galima susikurti nuosavą.
     */
    private class IteratorBst implements Iterator<E> {

        private final Stack<Node<E>> stack = new Stack<>();
        // Nurodo iteravimo kolekcija kryptį, true - didėjimo tvarka, false - mažėjimo
        private final boolean ascending;
        // Nurodo einamojo medžio elemento tėvą. Reikalingas šalinimui.
        private Node<E> parent = root;
        private Node<E> n;

        IteratorBst(boolean ascendingOrder) {
            this.ascending = ascendingOrder;
            this.toStack(root);
        }

        @Override
        public boolean hasNext() {
            return !stack.empty();
        }

        @Override
        public E next() {
            if (!stack.empty()) {
                // Grąžinamas paskutinis į steką patalpintas elementas
                n = stack.pop();
                // Atsimenama tėvo viršunė. Reikia remove() metodui
                parent = (!stack.empty()) ? stack.peek() : root;
                Node<E> node = (ascending) ? n.right : n.left;
                // Dešiniajame n pomedyje ieškoma minimalaus elemento,
                // o visi paieškos kelyje esantys elementai talpinami į steką
                toStack(node);
                return n.element;
            } else { // Jei stekas tuščias
                return null;
            }
        }

        @Override
        public void remove() {
            removeRecursive(n.element, parent);
        }

        private void toStack(Node<E> n) {
            while (n != null) {
                stack.push(n);
                n = (ascending) ? n.left : n.right;
            }
        }

        @Override
        public String toString() {
            return n != null ? n.element.toString() : "null";
        }
    }

    /**
     * Vidinė kolekcijos mazgo klasė
     *
     * @param <N> mazgo elemento duomenų tipas
     */
    protected static class Node<N> {

        // Elementas
        protected N element;
        // Rodyklė į kairįjį pomedį
        protected Node<N> left;
        // Rodyklė į dešinįjį pomedį
        protected Node<N> right;
        protected Node<N> parent;

        protected Node() {
        }

        protected Node(N element) {
            this.element = element;
            this.left = null;
            this.right = null;
            this.parent = null;
        }

        protected Node(N element, Node<N> Parent) {
            this.element = element;
            this.left = null;
            this.right = null;
            this.parent = Parent;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Node other = (Node) obj;
            if (element == null) {
                if (other.element != null)
                    return false;
            } else if (!element.equals(other.element))
                return false;
            return true;
        }
    }
}
