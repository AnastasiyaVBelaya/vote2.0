package by.it_academy.jd2.controller.servlet;

import by.it_academy.jd2.dto.genre.GenreResultDTO;
import by.it_academy.jd2.dto.genre.GenreSaveDTO;
import by.it_academy.jd2.service.api.IGenreService;
import by.it_academy.jd2.service.factory.ServiceFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

@WebServlet(urlPatterns = "/browser/genre")
public class BrowserGenreServlet extends HttpServlet {

    private static final IGenreService genreService = ServiceFactory.getGenreService();
    private static final String SUCCESS_JSP = "/template/genre/success.jsp";
    private static final String FORM_JSP = "/template/genre/form.jsp";
    private static final String ACTION_PARAM = "action";
    private static final String ID_PARAM = "id";
    private static final String GENRE_NAME_PARAM = "genreName";
    private static final String GENRE_DESCRIPTION_PARAM = "genreDescription";
    private static final String DELETE_ACTION = "delete";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<Long, GenreResultDTO> genres = genreService.get();
        req.setAttribute("genres", genres);
        req.getRequestDispatcher(FORM_JSP).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter(ACTION_PARAM);

        if (DELETE_ACTION.equals(action)) {
            delete(req, resp);
        } else {
            create(req, resp);
        }
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParam = req.getParameter(ID_PARAM);
        if (idParam != null && !idParam.isEmpty()) {
            try {
                Long id = Long.parseLong(idParam);
                genreService.delete(id);
                resp.sendRedirect(req.getContextPath() + "/browser/genre");
            } catch (NumberFormatException e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Некорректный ID жанра");
            }
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Отсутствует ID жанра");
        }
    }

    private void create(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String genreName = req.getParameter(GENRE_NAME_PARAM);
        String genreDescription = req.getParameter(GENRE_DESCRIPTION_PARAM);

        if (genreName != null && genreDescription != null) {
            try {
                GenreSaveDTO genreSaveDTO = new GenreSaveDTO(genreName, genreDescription);
                genreService.create(genreSaveDTO);
                req.setAttribute("genres", genreService.get());
                req.getRequestDispatcher(SUCCESS_JSP).forward(req, resp);
            } catch (IllegalArgumentException e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
            }
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Отсутствуют необходимые данные для жанра");
        }
    }
}
