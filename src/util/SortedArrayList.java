package util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;

public class SortedArrayList<E> extends ArrayList<E> {
    private Comparator<E> cmp;

    public SortedArrayList(Comparator<E> cmp) {
        this.cmp = cmp;
    }
    @Override
    public boolean add(E e) {
        int i = 0, end = this.size();

        while(i < end && cmp.compare(this.get(i), e) <= 0){
            i++;
        } // Pendiente revision

        /*while(ini< end)  { // Binary search
            mid = (ini + end)/2;
            if( cmp.compare( this.get(mid), e) < 0){
                end = mid;
            }
            else if( cmp.compare(this.get(mid), e) > 0){
                ini = mid;
            }
            else // They have the same time so we place the element e next to the mid index
                ini = end = mid + 1;
        } */
        super.add(i, e);
        return true;
    }
    @Override
    public boolean addAll(Collection<? extends E> c) {

        for(E e : c){
            if(!this.add(e))
                return false;
        }
        return true;
    }


    // Metodo sobrescrito para evitar el acceso a la lista con indice
    @Override
    public E set(int index, E element) {
        throw new UnsupportedOperationException("SortedArrayList doesn't support index operations\n");
    }

    // Metodo sobrescrito para evitar el acceso a la lista con indice
    @Override
    public void add(int index, E element) {
        throw new UnsupportedOperationException("SortedArrayList doesn't support index operations\n");
    }

    // Metodo sobrescrito para evitar el acceso a la lista con indice
    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        throw new UnsupportedOperationException("SortedArrayList doesn't support index operations\n");
    }
}