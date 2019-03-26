package idv.code.ratpack;

import ratpack.error.ServerErrorHandler;
import ratpack.test.embed.EmbeddedApp;

import static org.junit.Assert.assertEquals;

public class RatpackTestCh0602 {
    public static void main(String... args) throws Exception {
        EmbeddedApp.fromHandlers(chain -> chain
                .prefix("api", api -> api
                        .register(r -> r.add(ServerErrorHandler.class, (context, throwable) ->
                                        context.render("api error: " + throwable.getMessage())
                                )
                        )
                        .all(ctx -> {
                            throw new Exception("in api - " + ctx.getRequest().getPath());
                        })
                )
                .register(r -> r.add(ServerErrorHandler.class, (ctx, throwable) ->
                                ctx.render("app error error: " + throwable.getMessage())
                        )
                )
                .all(ctx -> {
                    throw new Exception("in app - " + ctx.getRequest().getPath());
                })
        ).test(httpClient -> {
            assertEquals("api error: in api - api/foo", httpClient.get("api/foo").getBody().getText());
            assertEquals("app error error: in app - bar", httpClient.get("bar").getBody().getText());
        });
    }
}
