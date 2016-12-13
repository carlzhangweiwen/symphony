package org.b3log.latke.repository.jdbc.mapping.oracle;

import org.b3log.latke.repository.jdbc.mapping.Mapping;
import org.b3log.latke.repository.jdbc.util.FieldDefinition;

/**
 * Created by Zhang Weiwen on 2016/12/12.
 */
public class IntMapping implements Mapping {

        @Override
        public String toDataBaseSting(final FieldDefinition definition) {
            final StringBuilder builder = new StringBuilder(definition.getName()).append(" number");

//            if (!definition.getNullable()) {
//                builder.append(" not null");
//            }

            return builder.toString();
        }
    }