package KTE.Labs.controllers;

import KTE.Labs.models.Patient;
import KTE.Labs.services.PatientServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/v1/patient")
public class PatientController {

    private final PatientServices patientServices;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Patient createPatient(@Valid @RequestBody Patient patient) {
        return patientServices.createPatient(patient);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Patient getPatientById(@RequestParam Long patientId) {
        return patientServices.findById(patientId);
    }
}
