package by.it_academy.jd2.controller.servlet;

import by.it_academy.jd2.service.api.IVoteService;
import by.it_academy.jd2.service.factory.ServiceFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = "/browser/results")
public class BrowserResultsServlet extends HttpServlet {

    private static final IVoteService voteService = ServiceFactory.getVoteService();
    private static final String RESULTS_JSP = "/template/wholeResult.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Map<String, Object> results = voteService.getResults();

        Map<String, Integer> artistVotes = (Map<String, Integer>) results.get("artists");
        Map<String, Integer> genreVotes = (Map<String, Integer>) results.get("genres");
        List<String> aboutVotes = (List<String>) results.get("aboutVotes");

        req.setAttribute("artistVotes", artistVotes);
        req.setAttribute("genreVotes", genreVotes);
        req.setAttribute("aboutVotes", aboutVotes);

        req.getRequestDispatcher(RESULTS_JSP).forward(req, resp);
    }
}
