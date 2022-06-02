
package cn.spdb.harrier.server.master.dispath.select;

import java.util.Collection;



public interface Selector<T> {

    T select(Collection<T> source);
}
