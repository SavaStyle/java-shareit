package KTE.Labs.services.Impl;

import KTE.Labs.exceptions.ObjectNotFoundException;
import KTE.Labs.models.Doctor;
import KTE.Labs.repositories.DoctorRepository;
import KTE.Labs.services.DoctorServices;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DoctorServicesImpl implements DoctorServices {

    private final DoctorRepository doctorRepository;

    @Override
    public Doctor createDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    @Override
    public Doctor findById(Long id) {
        return doctorRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Не найден доктор с id " + id));
    }
}
