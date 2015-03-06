package com.gmail.alekmiel91.fxutils.fxspring.fxbinding.binder;

import com.gmail.alekmiel91.fxutils.fxspring.fxbinding.type.Direction;
import com.gmail.alekmiel91.fxutils.fxspring.fxbinding.annotation.FXBinding;
import com.gmail.alekmiel91.fxutils.fxspring.fxbinding.type.Bidirectional;
import com.gmail.alekmiel91.fxutils.fxspring.fxbinding.utils.BidirectionalFactory;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Aleksander Mielczarek
 * @since 2015-03-03
 */
@Component
public class CachedBinderFactory implements BinderFactory {

    private static final Table<Direction, Bidirectional, Binder> BINDER_MAP;

    static {
        BINDER_MAP = HashBasedTable.create(Direction.values().length, Bidirectional.values().length);
        BINDER_MAP.put(Direction.TARGET, Bidirectional.NONE, new BinderFrom());
        BINDER_MAP.put(Direction.TARGET, Bidirectional.STRING, new BinderStringConverter());
        BINDER_MAP.put(Direction.TARGET, Bidirectional.FORMAT, new BinderFormat());
        BINDER_MAP.put(Direction.SOURCE, Bidirectional.NONE, new BinderTo());
        BINDER_MAP.put(Direction.SOURCE, Bidirectional.STRING, BINDER_MAP.get(Direction.TARGET, Bidirectional.STRING));
        BINDER_MAP.put(Direction.SOURCE, Bidirectional.FORMAT, BINDER_MAP.get(Direction.TARGET, Bidirectional.FORMAT));
        BINDER_MAP.put(Direction.BIDIRECTIONAL, Bidirectional.NONE, new BinderBidirectional());
        BINDER_MAP.put(Direction.BIDIRECTIONAL, Bidirectional.STRING, BINDER_MAP.get(Direction.TARGET, Bidirectional.STRING));
        BINDER_MAP.put(Direction.BIDIRECTIONAL, Bidirectional.FORMAT, BINDER_MAP.get(Direction.TARGET, Bidirectional.FORMAT));
    }

    @Autowired
    private BidirectionalFactory bidirectionalFactory;

    @Override
    public Binder createBinder(FXBinding fxBinding) {
        return BINDER_MAP.get(fxBinding.direction(), bidirectionalFactory.createBidirectional(fxBinding));
    }
}
