package org.b3log.latke.repository.oracle;

import org.apache.commons.lang.StringUtils;
import org.b3log.latke.repository.jdbc.AbstractJdbcDatabaseSolution;
import org.b3log.latke.repository.jdbc.mapping.Mapping;
import org.b3log.latke.repository.jdbc.mapping.oracle.*;
import org.b3log.latke.repository.jdbc.util.FieldDefinition;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Zhang Weiwen on 2016/12/12.
 */
public class OracleJdbcDatabaseSolution extends AbstractJdbcDatabaseSolution {
    public OracleJdbcDatabaseSolution() {

        this.registerType("int", new NumberMapping());
        this.registerType("boolean", new BooleanMapping());
        this.registerType("long", new LongMapping());
        this.registerType("double", new DoubleMapping());
        this.registerType("String", new StringMapping());
        this.registerType("Date", new DateMapping());
        this.registerType("mediumtext", new StringMapping());
        this.registerType("text", new StringMapping());
    }

    public String queryPage(int start, int end, String selectSql, String filterSql, String orderBySql, String tableName) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM (");
        sql.append(selectSql).append(" from ").append(tableName);
        sql.append(" where 1 = 1 ");

        if(StringUtils.isNotBlank(filterSql)) {
            sql.append(" and ").append(filterSql);
        }

        sql.append(orderBySql);
        sql.append(") WHERE 1 =1  and rownum > ").append(start).append(" and rownum <= ").append(end);
        return sql.toString();
    }

    public String getRandomlySql(String tableName, int fetchSize) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT * FROM ").append(tableName).append(" where rownum <= ").append(fetchSize).append(" ORDER BY dbms_random.value() ");
        return sql.toString();
    }

    protected void createDropTableSql(StringBuilder dropTableSql, String tableName) {
        dropTableSql.append("DROP TABLE ").append(tableName).append(";");
    }

    protected void createTableHead(StringBuilder createTableSql, String tableName) {
        createTableSql.append("CREATE TABLE ").append(tableName).append("(");
    }

    protected void createTableBody(StringBuilder createTableSql, List<FieldDefinition> fieldDefinitions) {
        ArrayList keyDefinitionList = new ArrayList();
        Iterator i$ = fieldDefinitions.iterator();

        while(i$.hasNext()) {
            FieldDefinition fieldDefinition = (FieldDefinition)i$.next();
            String type = fieldDefinition.getType();
            if(type == null) {
                throw new RuntimeException("the type of fieldDefinitions should not be null");
            }

            Mapping mapping = (Mapping)this.getJdbcTypeMapping().get(type);
            if(mapping == null) {
                throw new RuntimeException("the type[" + fieldDefinition.getType() + "] is not register for mapping ");
            }

            createTableSql.append(mapping.toDataBaseSting(fieldDefinition)).append(",   ");
            if(fieldDefinition.getIsKey().booleanValue()) {
                keyDefinitionList.add(fieldDefinition);
            }
        }

        if(keyDefinitionList.size() < 0) {
            throw new RuntimeException("no key talbe is not allow");
        } else {
            createTableSql.append(this.createKeyDefinition(keyDefinitionList));
        }
    }

    private String createKeyDefinition(List<FieldDefinition> keyDefinitionList) {
        StringBuilder sql = new StringBuilder();
        sql.append(" PRIMARY KEY");
        boolean isFirst = true;

        FieldDefinition fieldDefinition;
        for(Iterator i$ = keyDefinitionList.iterator(); i$.hasNext(); sql.append(fieldDefinition.getName())) {
            fieldDefinition = (FieldDefinition)i$.next();
            if(isFirst) {
                sql.append("(");
                isFirst = false;
            } else {
                sql.append(",");
            }
        }

        sql.append(")");
        return sql.toString();
    }

    protected void createTableEnd(StringBuilder createTableSql) {
        createTableSql.append(")");
    }

    public void clearTableSql(StringBuilder clearTableSq, String tableName, boolean ifdrop) {
        if(ifdrop) {
            clearTableSq.append("DROP TABLE ").append(tableName);
        } else {
            clearTableSq.append("TRUNCATE TABLE ").append(tableName);
        }

    }
}
