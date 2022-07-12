package org.example.server;

import org.example.services.ArticleService;
import org.example.services.AuthService;

import java.util.List;

public class Routes {
   public static final List<EndpointHandler> handlers = List.of(
       new EndpointHandler("/api/good/?", "GET", ArticleService::getAllArticles),
       new EndpointHandler("/api/good/?", "POST", ArticleService::createArticle),
       new EndpointHandler("/api/good/(\\d+)", "GET", ArticleService::getOneArticle),
       new EndpointHandler("/api/good/(\\d+)", "PUT", ArticleService::updateArticle),
       new EndpointHandler("/api/good/(\\d+)", "DELETE", ArticleService::deleteArticle),
       new EndpointHandler("/login", "POST", AuthService::processLogin)
   );
}
