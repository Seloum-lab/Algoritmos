/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import java.util.Objects;

/**
 *
 * @author DeLL
 */
public class Pair<K, V> {
    private K first;
    private V second;

    public Pair(K first, V second) {
        if (first == null || second == null) {
            throw new IllegalArgumentException("Neither element of Pair can be null");
        }
        this.first = first;
        this.second = second;
    }

    public void setFirst(K first) {
        this.first = first;
    }

    public void setSecond(V second) {
        this.second = second;
    }
    
    

    public K getFirst() {
        return first;
    }

    public V getSecond() {
        return second;
    }
    
    @Override
    public boolean equals(Object obj) {
        // Vérification de la référence de l'objet
        if (this == obj) return true;

        // Vérification si l'objet est de type Pair
        if (obj == null || getClass() != obj.getClass()) return false;

        // Casting de l'objet en Pair
        Pair<?, ?> pair = (Pair<?, ?>) obj;

        // Comparaison des éléments avec .equals() pour les objets génériques
        return (first == null ? pair.first == null : first.equals(pair.first)) &&
               (second == null ? pair.second == null : second.equals(pair.second));
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.first);
        hash = 89 * hash + Objects.hashCode(this.second);
        return hash;
    }

}

