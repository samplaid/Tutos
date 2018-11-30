package lu.wealins.liability.services.core.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Value;
import org.tempuri.wssqtdset.WssqtdsetExport;
import org.tempuri.wssqtdset.WssqtdsetExport.ExpBatchRestartBatchRestart;
import org.tempuri.wssqtdset.WssqtdsetExport.ExpQtaQueuedTasks;
import org.tempuri.wssqtdset.WssqtdsetImport;
import org.tempuri.wssqtdset.WssqtdsetImport.ImpBatchRestartBatchRestart;
import org.tempuri.wssqtdset.WssqtdsetImport.ImpQtaQueuedTasks;
import org.tempuri.wssqtdset.WssqtdsetImport.ImpValidationUsers;

import lu.wealins.liability.services.core.utils.StringToTrimString;
import lu.wealins.common.dto.liability.services.FundTransactionsValuationRequest;

@Mapper(componentModel = "spring", uses = { StringToTrimString.class })
public abstract class WssqtdsetImportMapper {

	@Value("${liability.ws.credential.login}")
	private String wsLoginCredential;

	@Value("${liability.ws.credential.password}")
	private String wsPasswordCredential;

	@Mappings({
			@Mapping(source = "date", target = "impFtdFundTransactionDates.date", dateFormat = "yyyyMMdd"),
			@Mapping(source = "fundId", target = "impFtdFundTransactionDates.fund")
	})
	public abstract WssqtdsetImport asWssqtdsetImport(FundTransactionsValuationRequest fundTransactionsValuationRequest);

	@Mappings({
			@Mapping(source = "expBatchRestartBatchRestart", target = "impBatchRestartBatchRestart"),
			@Mapping(source = "expQtaQueuedTasks", target = "impQtaQueuedTasks")
	})
	public abstract WssqtdsetImport asWssqtdsetImport(WssqtdsetExport in);

	public abstract ImpBatchRestartBatchRestart asImpBatchRestartBatchRestart(ExpBatchRestartBatchRestart in);

	public abstract ImpQtaQueuedTasks asImpQtaQueuedTasks(ExpQtaQueuedTasks in);

	@AfterMapping
	public WssqtdsetImport afterEntityMapping(WssqtdsetExport in, @MappingTarget WssqtdsetImport out) {

		return setupValidationUsers(out);
	}

	private WssqtdsetImport setupValidationUsers(WssqtdsetImport out) {
		ImpValidationUsers impValidationUsers = new ImpValidationUsers();
		impValidationUsers.setLoginId(wsLoginCredential);
		impValidationUsers.setPassword(wsPasswordCredential);

		out.setImpValidationUsers(impValidationUsers);
		return out;
	}

	@AfterMapping
	public WssqtdsetImport afterEntityMapping(FundTransactionsValuationRequest in, @MappingTarget WssqtdsetImport out) {

		return setupValidationUsers(out);
	}

}
