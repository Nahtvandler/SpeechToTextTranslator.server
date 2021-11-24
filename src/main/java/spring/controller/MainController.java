package spring.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import spring.DTOs.LoginPair;
import spring.DTOs.RoomHttpPojo;
import spring.DTOs.UserHTTPPojo;
import spring.service.ClientService;
import spring.service.RoomService;


@Controller
@RequestMapping("/")
@Transactional
public class MainController {
    private final static Logger logger = LogManager.getLogger(MainController.class);

    @Autowired
    ClientService clientService;

    @Autowired
    RoomService roomService;

    public MainController(ClientService clientService, RoomService roomService) {
        this.clientService = clientService;
        this.roomService = roomService;
    }

    @RequestMapping("/")
    public @ResponseBody String index() {
        logger.debug("ping");
        return "HI THERE";
    }

    @RequestMapping("/registration")
    public @ResponseBody String registerUser(@RequestBody UserHTTPPojo user) {
        logger.debug("Registration request {}", user);

        Boolean result = clientService.registerUser(user);
        return result ? HttpStatus.OK.toString() : HttpStatus.INTERNAL_SERVER_ERROR.toString();
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public @ResponseBody LoginPair doLogin(@RequestParam("login") String login, @RequestParam("password") String password) {
        logger.debug("Login request with login: {}, password {}", login, password);

        LoginPair loginPair = clientService.doLogin(login, password);
        return loginPair;
    }

    @RequestMapping("/createRoom")
    public @ResponseBody RoomHttpPojo createRoom(@RequestBody RoomHttpPojo request) {

        RoomHttpPojo roomHttpPojo;
        try {
            roomHttpPojo = roomService.createRoom(request);
        } catch (Exception e) {
            logger.error("An error occurred while creating the room: " + request.getName());
            return null;
        }

        return roomHttpPojo;
    }

    @RequestMapping("/connectToRoom")
    public @ResponseBody RoomHttpPojo connectToRoom(@RequestParam("id") Integer id) {
        // TODO logging
        return roomService.findRoomById(id);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public @ResponseBody String getAllUsers() {
        return clientService.getAllClients();
    }
}
