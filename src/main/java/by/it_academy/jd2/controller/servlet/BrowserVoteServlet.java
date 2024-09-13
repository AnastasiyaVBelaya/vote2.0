package by.it_academy.jd2.controller.servlet;

import by.it_academy.jd2.dto.artist.ArtistResultDTO;
import by.it_academy.jd2.dto.genre.GenreResultDTO;
import by.it_academy.jd2.dto.vote.VoteSaveDTO;
import by.it_academy.jd2.service.api.IArtistService;
import by.it_academy.jd2.service.api.IGenreService;
import by.it_academy.jd2.service.api.IVoteService;
import by.it_academy.jd2.service.factory.ServiceFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = "/browser/vote")
public class BrowserVoteServlet extends HttpServlet {

    private static final IVoteService voteService = ServiceFactory.getVoteService();
    private static final IArtistService artistService = ServiceFactory.getArtistService();
    private static final IGenreService genreService = ServiceFactory.getGenreService();

    private static final String SENDED_HEADER_NAME = "sended";
    private static final String VOTE_FORM_JSP = "/template/voteForm.jsp";
    private static final String VOTE_RESULT_JSP = "/template/voteResult.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        List<ArtistResultDTO> artists = new ArrayList<>(artistService.get().values());
        List<GenreResultDTO> genres = new ArrayList<>(genreService.get().values());

        req.setAttribute("artists", artists);
        req.setAttribute("genres", genres);

        req.getRequestDispatcher(VOTE_FORM_JSP).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Cookie[] cookies = req.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (SENDED_HEADER_NAME.equalsIgnoreCase(cookie.getName())) {
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Вы уже голосовали");
                    return;
                }
            }
        }

        try {
            Long artistId = Long.parseLong(req.getParameter("artist"));
            String[] genreStrings = req.getParameterValues("genre");
            List<Long> genreIds = Arrays.stream(genreStrings)
                    .map(Long::parseLong)
                    .collect(Collectors.toList());
            String about = req.getParameter("about");

            voteService.create(new VoteSaveDTO(artistId, genreIds, about));

            Cookie myCookie = new Cookie(SENDED_HEADER_NAME, "true");
            myCookie.setMaxAge(Math.toIntExact(TimeUnit.DAYS.toSeconds(1)));
            resp.addCookie(myCookie);

            req.setAttribute("artist", artistId);
            req.setAttribute("genres", String.join(", ", genreStrings));
            req.setAttribute("about", about);

            req.getRequestDispatcher(VOTE_RESULT_JSP).forward(req, resp);

        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    "Некорректный формат данных: " + e.getMessage());
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Ошибка при обработке данных: " + e.getMessage());
        }
    }
}
