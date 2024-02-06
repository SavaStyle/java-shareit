package KTE.Labs.controllers;

import KTE.Labs.models.Doctor;
import KTE.Labs.services.DoctorServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/v1/doctor")
public class DoctorController {

    private final DoctorServices doctorServices;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Doctor createDoctor(@Valid @RequestBody Doctor doctor) {
        return doctorServices.createDoctor(doctor);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Doctor getDoctorById(@RequestParam Long docId) {
        return doctorServices.findById(docId);
    }
}
