package controllers;

import java.util.concurrent.CompletionStage;

import play.mvc.Action.Simple;
import play.mvc.Http.Context;
import play.mvc.Result;
import utils.ExceptionMailer;

public class CatchAction extends Simple {

	@Override
	public CompletionStage<Result> call(Context arg0) {
		try {
			return delegate.call(arg0);			
		} catch (Throwable e) {
			ExceptionMailer.send(e);
			throw new RuntimeException();
		}
	}

}
