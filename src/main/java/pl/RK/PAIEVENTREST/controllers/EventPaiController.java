package pl.RK.PAIEVENTREST.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.RK.PAIEVENTREST.models.EventPAI;
import pl.RK.PAIEVENTREST.models.dto.CommentDto;
import pl.RK.PAIEVENTREST.models.dto.EventPAIDto;
import pl.RK.PAIEVENTREST.models.dto.UserPAIDto;
import pl.RK.PAIEVENTREST.models.enums.AccessPAI;
import pl.RK.PAIEVENTREST.services.implementations.EventPaiServiceImp;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/event")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class EventPaiController {

    EventPaiServiceImp eventPaiService;

    @Autowired
    public EventPaiController(EventPaiServiceImp eventPaiService) {
        this.eventPaiService = eventPaiService;
    }

    @GetMapping("/comments/{eventId}")
    public List<CommentDto> getAllComments(@PathVariable @NotBlank int eventId) {
        return eventPaiService.getAllComments(eventId).stream().map(CommentDto::new).collect(Collectors.toList());
    }

    @PostMapping("/{name}/{prov}/{city}/{address}/{accessPAI}/{dateOfStartEvent}/{email}/{x}/{y}")
    public EventPAIDto addEvent(@PathVariable @NotBlank String name, @PathVariable @NotBlank String prov, @PathVariable @NotBlank String city
            , @NotBlank @PathVariable String address, @PathVariable AccessPAI accessPAI
            , @PathVariable LocalDateTime dateOfStartEvent,
                                @Pattern(regexp = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$", message = "incorrect email")
                                @PathVariable String email, @PathVariable double x, @PathVariable double y) {
        return new EventPAIDto(eventPaiService.set(name, prov, city, address, accessPAI, dateOfStartEvent, email, x, y));
    }

    @DeleteMapping("/{eventId}")
    public boolean deleteEvent(@PathVariable @NotBlank int eventId) {
        return eventPaiService.delete(eventId);
    }

    @GetMapping("/{eventId}")
    public EventPAIDto get(@PathVariable @NotBlank int eventId) {
        return new EventPAIDto(eventPaiService.get(eventId));
    }

    @GetMapping
    public List<EventPAIDto> getAll(Principal principal) {
        return eventPaiService.getAll().stream().parallel().map(EventPAIDto::new).collect(Collectors.toList());
    }

    @GetMapping("/without/{email}")
    public List<EventPAI> getAllWithoutUser(@PathVariable
                                            @Pattern(regexp = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$", message = "incorrect email")
                                                    String email) {
        return eventPaiService.getAllWithoutUserEvent(email);
    }

    @GetMapping("/users/{eventId}")
    public Set<UserPAIDto> getAllUsers(@PathVariable @NotBlank int eventId) {
        return eventPaiService.getAllUsers(eventId).stream().map(UserPAIDto::new).collect(Collectors.toSet());
    }

    @GetMapping("/admins/{eventId}")
    public Set<UserPAIDto> getAllAdmins(@PathVariable @NotBlank int eventId) {
        return eventPaiService.getAllAdmins(eventId).stream().map(UserPAIDto::new).collect(Collectors.toSet());
    }

    @PutMapping("/changeAccess/{eventId}")
    public boolean changeAccess(@PathVariable @NotBlank int eventId) {
        return eventPaiService.changeAccess(eventId);
    }

    @PutMapping("/setAdmin/{email}/{eventId}")
    public boolean setUserAsAdmin(@PathVariable
                                  @Pattern(regexp = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$", message = "incorrect email")
                                          String email, @PathVariable @NotBlank int eventId) {
        return eventPaiService.setUserAsAdmin(email, eventId);
    }

    //dodanie do zapro
    @PostMapping("/addUser/{email}/{eventId}")
    public boolean addUser(@PathVariable
                           @Pattern(regexp = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$", message = "incorrect email")
                                   String email, @PathVariable @NotBlank int eventId) {
        return eventPaiService.addUser(email, eventId);
    }

    //akceptacja prośby o dołączenie
    @PutMapping("/acceptPart/{participationId}/{eventId}")
    public boolean acceptParticipation(@PathVariable @NotBlank int participationId, @PathVariable @NotBlank int eventId) {
        return eventPaiService.acceptParticipation(participationId, eventId);
    }

    //uzupełnienie geolokalizacji
    @PutMapping("/geo/{eventId}/{x}/{y}")
    public boolean setGeo(@PathVariable @NotBlank int eventId, @PathVariable @NotBlank double x, @PathVariable @NotBlank double y) {
        return eventPaiService.setGeoLocal(eventId, x, y);
    }

    @GetMapping("/city/{city}")
    public List<EventPAIDto> findAllByCity(@PathVariable @NotBlank String city) {
        return eventPaiService.getAllByCity(city).stream().parallel()
                .map(EventPAIDto::new).collect(Collectors.toList());
    }

    @GetMapping("/geo/{eventId}/r/{range}")
    public List<EventPAIDto> findAllInRange(@PathVariable @NotBlank int eventId
            , @PathVariable @NotBlank double range) {
        return eventPaiService.getAllInRange(eventId, range).stream().parallel()
                .map(EventPAIDto::new).collect(Collectors.toList());
    }

    @GetMapping("/geoXY/{x}/{y}/{range}")
    public List<EventPAIDto> findAllInRangeByGeo(@PathVariable @NotBlank double x
            , @PathVariable @NotBlank double y
            , @PathVariable @NotBlank double range) {
        return eventPaiService.getAllInRangeByGeoLocation(x, y, range)
                .stream().parallel()
                .map(EventPAIDto::new).collect(Collectors.toList());
    }


}
