package org.example.repositories;

import org.example.enums.CreditStatus;
import org.example.enums.CreditType;
import org.example.enums.SourceType;
import org.example.interfaces.CreditInterface;
import org.example.models.Credit;
import org.example.models.FeeRuleCredit;
import org.postgresql.PGConnection;
import org.postgresql.util.PGobject;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CreditRepository extends BaseRepository  implements CreditInterface {

    public boolean  requestCredit(int account_id, CreditType creditType , BigDecimal amount, int  durationMonths, String justification,int fee_rule_id ,BigDecimal monthlyPayment,BigDecimal fortyPercentSalary,BigDecimal monthlyInterest) {
        String query = "insert into credits (account_id,amount,duration_months,justification,credit_type,fee_rule_credit_id,monthly_payment,fortyPercentSalary,month_interest,remaining_amount,remaining_duration) values (?,?,?,?,?,?,?,?,?,?,?)";
        try(PreparedStatement stmt = conn().prepareStatement(query)){
            stmt.setInt(1,account_id);
            stmt.setBigDecimal(2,amount);
            stmt.setInt(3,durationMonths);
            stmt.setString(4,justification);

            PGobject pgCreditType = new PGobject();
            pgCreditType.setType("credit_type");
            pgCreditType.setValue(creditType.name());
            stmt.setObject(5,pgCreditType);

            stmt.setInt(6,fee_rule_id);
            stmt.setBigDecimal(7,monthlyPayment);
            stmt.setBigDecimal(8,fortyPercentSalary);
            stmt.setBigDecimal(9,monthlyInterest);
            stmt.setBigDecimal(10, amount);
            stmt.setInt(11,durationMonths);

            int result = stmt.executeUpdate();
            if(result>0){
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public FeeRuleCredit interestRate(CreditType creditType,int durationMonths){
        String query = "select * from fee_rule_credit where credit_type = ? and ? BETWEEN min_duration AND max_duration  and isactive = true";
        try (PreparedStatement stmt = conn().prepareStatement(query)){
            PGobject pgCreditType = new PGobject();
            pgCreditType.setType("credit_type");
            pgCreditType.setValue(creditType.name());
            stmt.setObject(1, pgCreditType);
            stmt.setInt(2, durationMonths);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
               return  new FeeRuleCredit(rs.getInt("id"), CreditType.valueOf(rs.getString("credit_type")),rs.getBigDecimal("interest_rate"));
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
       return null;
    }

    public List<Credit> findActiveCredits(){
        String query = "SELECT c.id as credit_id , c.amount ,c.duration_months , c.monthly_payment," +
                " c.remaining_amount,c.justification,c.credit_type,c.status ,c.account_id,c.approved_at," +
                " c.remaining_duration,fr.interest_rate ,c.account_id " +
                "from credits c " +
                "inner join  fee_rule_credit fr on c.fee_rule_credit_id  = fr.id  " +
                "where c.status = 'ACTIVE' ";
        List<Credit> credits = new ArrayList<>();
        try(PreparedStatement stmt = conn().prepareStatement(query)){
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
             Credit credit = new Credit(
                        rs.getInt("credit_id"),
                        rs.getBigDecimal("amount"),
                        rs.getInt("duration_months"),
                        rs.getInt("remaining_duration"),
                        rs.getBigDecimal("monthly_payment"),
                        rs.getBigDecimal("remaining_amount"),
                        rs.getString("justification"),
                        CreditType.valueOf(rs.getString("credit_type")),
                        CreditStatus.valueOf(rs.getString("status")),
                        rs.getInt("account_id"),
                        rs.getBigDecimal("interest_rate")
                );
             credits.add(credit);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return credits;
    }

    public boolean updateCreditAfterPayment(int creditId, BigDecimal newRemainingPrincipal, int newRemainingDuration, CreditStatus newStatus) {
        String sql = "UPDATE credits " +
                "SET remaining_amount = ?, " +
                "remaining_duration = ?, " +
                "status = ? " +
                "WHERE id = ?";

        try (PreparedStatement stmt = conn().prepareStatement(sql)) {
            stmt.setBigDecimal(1, newRemainingPrincipal);
            stmt.setInt(2, newRemainingDuration);
            PGobject pgStatus = new PGobject();
            pgStatus.setType("credit_status");
            pgStatus.setValue(newStatus.name());
            stmt.setObject(3, pgStatus);
            stmt.setInt(4, creditId);
            int row =  stmt.executeUpdate();
            if(row>0){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int AddRevenueCredit(SourceType type, BigDecimal monthlyPayment, int  creditId){
      String query = "insert into banke_revenue (source_type, revenue, credit_id) values (?,?,?)";
      try(PreparedStatement stmt = conn().prepareStatement(query , PreparedStatement.RETURN_GENERATED_KEYS)){
          PGobject pgSourceType = new PGobject();
          pgSourceType.setType("source_type");
          pgSourceType.setValue(type.name());
          stmt.setObject(1,pgSourceType);
          stmt.setBigDecimal(2,monthlyPayment);
          stmt.setInt(3,creditId);
          int row = stmt.executeUpdate();
           if(row>0){
               ResultSet rs = stmt.getGeneratedKeys();
               if(rs.next()){
                  return rs.getInt(1);
               }
           }
      }catch(SQLException e){
          throw new RuntimeException(e);
      }
      return -1;
    }





}
