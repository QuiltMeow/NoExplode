package ew.sr.x1c.quilt.meow.plugin.NoExplode.util.data;

import java.io.Serializable;

public class Pair<E, F> implements Serializable {

    public E left;
    public F right;

    public E getLeft() {
        return left;
    }

    public F getRight() {
        return right;
    }

    public void setLeft(E data) {
        left = data;
    }

    public void setRight(F data) {
        right = data;
    }

    public Pair(E left, F right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return left.toString() + " - " + right.toString();
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = prime * result + (left == null ? 0 : left.hashCode());
        result = prime * result + (right == null ? 0 : right.hashCode());
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (getClass() != object.getClass()) {
            return false;
        }

        Pair other = (Pair) object;
        if (left == null) {
            if (other.left != null) {
                return false;
            }
        } else if (!left.equals(other.left)) {
            return false;
        }

        if (right == null) {
            if (other.right != null) {
                return false;
            }
        } else if (!right.equals(other.right)) {
            return false;
        }
        return true;
    }
}
