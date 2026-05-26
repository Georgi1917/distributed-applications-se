package com.example.JobListing.Controller;

import com.example.JobListing.Entities.Enums.UserRole;
import com.example.JobListing.Infrastructure.RequestDTOs.UserDTOs.UpdateDTO;
import com.example.JobListing.Infrastructure.RequestDTOs.UserDTOs.UserCreationDTO;
import com.example.JobListing.Infrastructure.ResponseDTOs.UserResponseDTO;
import com.example.JobListing.Service.Implementation.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user/")
public class UserController {

    private final UserService _service;

    public UserController(UserService service)
    {
        _service = service;
    }

    @GetMapping("/")
    public Page<UserResponseDTO> GetAllUsers(
            @RequestParam(required = false) Integer listing_id,
            @RequestParam(required = false) String searchBy,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "true") boolean asc,
            Authentication auth
    )
    {

        Sort sort = asc ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        searchBy = (searchBy == null) ? "" : searchBy;

        if (listing_id != null)
        {
            return _service.GetUsersByListing(pageable, listing_id, searchBy).join();
        }

        System.out.println(auth.getName());
        System.out.println(auth.getAuthorities().toArray()[0].toString().contentEquals("ROLE_ADMIN"));

        return _service.GetAllUsers(pageable, searchBy).join();

    }

    @GetMapping("/{id}")
    public UserResponseDTO GetUser(@PathVariable int id)
    {

        return _service.GetUser(id).join();

    }

    @PostMapping("/")
    public UserResponseDTO SaveUser(@Valid @RequestBody UserCreationDTO user)
    {

        return _service.SaveUser(user).join();

    }

    @PutMapping("/update/{id}")
    public UserResponseDTO UpdateUser(@PathVariable int id, @Valid @RequestBody UpdateDTO entity)
    {

        return _service.UpdateUser(id, entity).join();

    }

    @DeleteMapping("/delete/{id}")
    public UserResponseDTO DeleteUser(@PathVariable int id)
    {

        return _service.DeleteUser(id).join();

    }

}
