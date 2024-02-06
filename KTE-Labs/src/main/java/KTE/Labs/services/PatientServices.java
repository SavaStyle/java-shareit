package KTE.Labs.services;

import KTE.Labs.models.Patient;

public interface PatientServices {
    Patient createPatient(Patient patient);

    Patient findById(Long id);
}
