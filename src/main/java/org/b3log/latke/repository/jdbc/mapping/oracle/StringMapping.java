package org.b3log.latke.repository.jdbc.mapping.oracle;

import org.b3log.latke.repository.jdbc.mapping.Mapping;
import org.b3log.latke.repository.jdbc.util.FieldDefinition;

/**
 * Created by Zhang Weiwen on 2016/12/12.
 */
public class StringMapping implements Mapping {

    @Override
    public String toDataBaseSting(final FieldDefinition definition) {
        final StringBuilder sql = new StringBuilder();

        sql.append(definition.getName());

        if (definition.getLength() == null) {
            definition.setLength(new Integer("0"));
        }

        final Integer length = definition.getLength();

        if (length > new Integer("4000")) {
                sql.append(" clob");
        } else {
            sql.append(" varchar2(").append(length < 1 ? new Integer("100") : length);
            sql.append(")");
        }

//        if (!definition.getNullable()) {
//            sql.append(" not null");
//        }

        return sql.toString();
    }
}
