package KTE.Labs.services.Impl;

import KTE.Labs.exceptions.ObjectNotFoundException;
import KTE.Labs.models.Patient;
import KTE.Labs.repositories.PatientRepository;
import KTE.Labs.services.PatientServices;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatientServicesImpl implements PatientServices {

    private final PatientRepository patientRepository;

    @Override
    public Patient createPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    @Override
    public Patient findById(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Не найден пациент с id " + id));
    }
}
