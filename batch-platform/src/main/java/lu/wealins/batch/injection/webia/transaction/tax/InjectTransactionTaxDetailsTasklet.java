package lu.wealins.batch.injection.webia.transaction.tax;

import javax.ws.rs.core.Response;

import org.springframework.batch.core.scope.context.ChunkContext;

import lu.wealins.batch.simplerest.SimpleRestTaskLet;

public class InjectTransactionTaxDetailsTasklet extends SimpleRestTaskLet<Void, Response> {

	@Override
	public Void createRequest(ChunkContext chunkContext) {
		return null;
	}


	@Override
	public String createExitMessage(Response Response) {
		return null;
	}
}
