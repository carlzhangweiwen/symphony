package org.b3log.latke.repository.jdbc.mapping.oracle;

import org.b3log.latke.repository.jdbc.mapping.Mapping;
import org.b3log.latke.repository.jdbc.util.FieldDefinition;

/**
 * Created by Zhang Weiwen on 2016/12/12.
 */
public class NumberMapping implements Mapping {

    @Override
    public String toDataBaseSting(final FieldDefinition definition) {

        final StringBuilder sql = new StringBuilder();

        sql.append(definition.getName());
        sql.append("  number ");
//        if (!definition.getNullable()) {
//            sql.append(" not null");
//        }

        return sql.toString();

    }

}