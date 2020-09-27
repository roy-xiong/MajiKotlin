package com.roy.kotlin.test.greendao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.roy.kotlin.test.entity.InfoEntity;

import com.roy.kotlin.test.greendao.InfoEntityDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig infoEntityDaoConfig;

    private final InfoEntityDao infoEntityDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        infoEntityDaoConfig = daoConfigMap.get(InfoEntityDao.class).clone();
        infoEntityDaoConfig.initIdentityScope(type);

        infoEntityDao = new InfoEntityDao(infoEntityDaoConfig, this);

        registerDao(InfoEntity.class, infoEntityDao);
    }
    
    public void clear() {
        infoEntityDaoConfig.clearIdentityScope();
    }

    public InfoEntityDao getInfoEntityDao() {
        return infoEntityDao;
    }

}