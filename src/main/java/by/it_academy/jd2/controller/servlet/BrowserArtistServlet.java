package by.it_academy.jd2.controller.servlet;

import by.it_academy.jd2.dto.artist.ArtistResultDTO;
import by.it_academy.jd2.dto.artist.ArtistSaveDTO;
import by.it_academy.jd2.service.api.IArtistService;
import by.it_academy.jd2.service.factory.ServiceFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

@WebServlet(urlPatterns = "/browser/artist")
public class BrowserArtistServlet extends HttpServlet {

    private static final IArtistService artistService = ServiceFactory.getArtistService();
    private static final String SUCCESS_JSP = "/template/artist/success.jsp";
    private static final String FORM_JSP = "/template/artist/form.jsp";
    private static final String ACTION_PARAM = "action";
    private static final String ID_PARAM = "id";
    private static final String ARTIST_NAME_PARAM = "artistName";
    private static final String GENRE_PARAM = "genre";
    private static final String BIOGRAPHY_PARAM = "biography";
    private static final String DELETE_ACTION = "delete";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<Long, ArtistResultDTO> artists = artistService.get();
        req.setAttribute("artists", artists);
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
                artistService.delete(id);
                resp.sendRedirect(req.getContextPath() + "/browser/artist");
            } catch (NumberFormatException e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Некорректный ID исполнителя");
            }
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Отсутствует ID исполнителя");
        }
    }

    private void create(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String artistName = req.getParameter(ARTIST_NAME_PARAM);
        String genre = req.getParameter(GENRE_PARAM);
        String biography = req.getParameter(BIOGRAPHY_PARAM);

        if (artistName != null && genre != null && biography != null) {
            try {
                ArtistSaveDTO artistSaveDTO = new ArtistSaveDTO(artistName, genre, biography);
                artistService.create(artistSaveDTO);
                req.setAttribute("artists", artistService.get());
                req.getRequestDispatcher(SUCCESS_JSP).forward(req, resp);
            } catch (Exception e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Некорректные данные для артиста");
            }
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Отсутствуют необходимые данные для артиста");
        }
    }
}