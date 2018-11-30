/**
 * 
 */
package lu.wealins.batch.mapper;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import lu.wealins.rest.model.common.AccountingNavToInject;


public class CloturedVniRecordFieldSetMapper implements FieldSetMapper<AccountingNavToInject> {

	private final String dateFormat = "yyyy.MM.dd";
	@Override
	public AccountingNavToInject mapFieldSet(FieldSet fieldSet) throws BindException {

		AccountingNavToInject accountingNavtoInject = new AccountingNavToInject();

		accountingNavtoInject.setFund(fieldSet.readString(0));
		accountingNavtoInject.setNavAmount(fieldSet.readBigDecimal(1));
		accountingNavtoInject.setCurrency(fieldSet.readString(2));
		accountingNavtoInject.setNavDate(fieldSet.readDate(3, dateFormat));
		accountingNavtoInject.setCreateDate(fieldSet.readDate(4, dateFormat));
		accountingNavtoInject.setIsinCode(fieldSet.readString(5));
		accountingNavtoInject.setFundType(fieldSet.readString(6));
		accountingNavtoInject.setOperationType(fieldSet.readString(7));

		return accountingNavtoInject;
	}

}
