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
        int ini = 0, end = this.size(), mid;

        while(ini< end)  {
            mid = (ini + end)/2;
            if( cmp.compare( this.get(mid), e) < 0){
                end = mid;
            }
            else if( cmp.compare(this.get(mid), e) > 0){
                ini = mid;
            }
            else // En este caso, es como contains() == true, por lo que no lo añade para evitar elementos duplicados
                return false;

        }
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
        throw new UnsupportedOperationException("SortedArrayList doesn't support index operations");
    }

    // Metodo sobrescrito para evitar el acceso a la lista con indice
    @Override
    public void add(int index, E element) {
        throw new UnsupportedOperationException("SortedArrayList doesn't support index operations");
    }

    // Metodo sobrescrito para evitar el acceso a la lista con indice
    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        throw new UnsupportedOperationException("SortedArrayList doesn't support index operations");
    }
}