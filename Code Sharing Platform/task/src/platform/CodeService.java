package platform;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Period;
import java.util.List;

@Service
public class CodeService {

    @Autowired
    CodeRepository codeRepository;

    public Code getCodeById(Long id) {
        return codeRepository.findById(id).get();
    }

    public void saveOrUpdate(Code code) {
        codeRepository.save(code);
    }

    public void delete(Long id) {
        codeRepository.deleteById(id);
    }

}
