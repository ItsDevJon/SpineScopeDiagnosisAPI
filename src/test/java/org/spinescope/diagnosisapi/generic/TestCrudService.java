package org.spinescope.diagnosisapi.generic;

import org.springframework.data.repository.CrudRepository;

public class TestCrudService extends AbstractCrudService<String, Long> {

    public TestCrudService(CrudRepository<String, Long> repository) {
        super(repository);
    }

}
