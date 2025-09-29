package org.example.repositories;


import org.example.enums.CurrencyType;
import org.example.enums.ModeRule;
import org.example.enums.OperationType;
import org.example.interfaces.FeeruleInterface;
import org.example.models.FeeRule;
import org.postgresql.util.PGobject;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class FeeruleRepository extends BaseRepository implements FeeruleInterface  {

    public int create(OperationType operationType, ModeRule modeRule, CurrencyType currencyType, BigDecimal value){
            FeeRule feeRule = new FeeRule(operationType,modeRule,currencyType,value);

        String query = "insert into fee_rules (operation_type,mode,currency,value ,is_active) values (?,?,?,?,?)";
        try(PreparedStatement stmt = conn().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){
            PGobject operationObj = new PGobject();
            operationObj.setType("operation_type");
            operationObj.setValue(feeRule.getOperationType().name());
            stmt.setObject(1,operationObj);

            PGobject modeObj = new PGobject();
            modeObj.setType("mode_rule");
            modeObj.setValue(feeRule.getMode().name());
            stmt.setObject(2,modeObj);

            PGobject currencyObj = new PGobject();
            currencyObj.setType("currency_type");
            currencyObj.setValue(feeRule.getCurrency().name());
            stmt.setObject(3,currencyObj);

            stmt.setBigDecimal(4,value);
            stmt.setBoolean(5,false);

            int rows = stmt.executeUpdate();
            if(rows>0){
                ResultSet rs = stmt.getGeneratedKeys();
                if(rs.next()){
                    feeRule.setId(rs.getInt(1));
                    return rs.getInt(1);
                }
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return -1;
    }

    public List<FeeRule> getAll(){
        String query = "select * from fee_rules";
        List<FeeRule> feeRules = new ArrayList<>();
        try(PreparedStatement  stmt = conn().prepareStatement(query)){
            ResultSet rs  = stmt.executeQuery();
            while (rs.next()){
                feeRules.add(new FeeRule(
                        rs.getInt("id"),
                        OperationType.valueOf(rs.getString("operation_type")),
                        ModeRule.valueOf(rs.getString("mode")),
                        rs.getBigDecimal("value"),
                        CurrencyType.valueOf(rs.getString("currency")),
                        rs.getBoolean("is_active"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at").toLocalDateTime()
                        ));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return  feeRules;
    }

    public boolean update(int id , Map<String, Object> infoUpdate){
        StringBuilder query = new StringBuilder("update fee_rules set updated_at = ? ,");

        List<Object> values = new ArrayList<>();
        values.add(LocalDateTime.now());

        for(Map.Entry<String, Object> entry : infoUpdate.entrySet()){
            query.append(entry.getKey()).append(" = ?, ");
            values.add(entry.getValue());
        }
        query.delete(query.length()-2, query.length());
        query.append(" where id=?");
        values.add(id);

        try(PreparedStatement stmt = conn().prepareStatement(query.toString())){
            for(int i = 0; i < values.size(); i++){
                stmt.setObject( i+1, values.get(i));
            }
            int row  = stmt.executeUpdate();
            if (row > 0) {
                return true;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
            return false;
    }

    public boolean activate(int id){
      String query = "update fee_rules set is_active = true where id = ?";

      try(PreparedStatement stmt = conn().prepareStatement(query)){
          stmt.setInt(1,id);
          int row = stmt.executeUpdate();
          if(row>0){
              new FeeRule().setActive(true);
              return true;
          }
      }catch (SQLException e){
          e.printStackTrace();
      }
      return false;
    }

    public boolean deactivate(int id){
        String query = "update fee_rules set is_active = false where id = ?";
        try(PreparedStatement stmt = conn().prepareStatement(query)){
            stmt.setInt(1,id);
            int row = stmt.executeUpdate();
            if(row > 0){
                new FeeRule().setActive(false);
                return true;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return  false;
    }

    public FeeRule findByOperationTypeAndCurrencyAndIsActive(OperationType operationType,CurrencyType currencyType){
        String query = "select * from fee_rules where operation_type=? and currency_type=? and is_active = true" +
                "ORDER BY created_at DESC LIMIT 1 ";
        try(PreparedStatement stmt = conn().prepareStatement(query)){
            stmt.setObject(1,operationType);
            stmt.setObject(2,currencyType);

            try(ResultSet rs = stmt.executeQuery()){
                if(rs.next()){
                   return  new FeeRule(
                            rs.getInt("id"),
                            operationType,
                            ModeRule.valueOf(rs.getString("mode")),
                            rs.getBigDecimal("value"),
                            currencyType,
                            true,
                            rs.getTimestamp("creatred_at").toLocalDateTime(),
                            rs.getTimestamp("updated_at").toLocalDateTime()
                    );

                }
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
      return null;
    }


}
