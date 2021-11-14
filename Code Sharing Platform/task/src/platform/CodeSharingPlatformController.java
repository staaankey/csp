package platform;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@RestController
public class CodeSharingPlatformController {

    @Autowired
    LoadDate date;
    @Autowired
    CodeService codeService;

    List<Code> codeList = new ArrayList<>(Arrays.asList());
    private Long idCounter = 1L; // id 1 exists already

    // API endpoint for posting code snippet
    @PostMapping(value = "/api/code/new", produces = "application/json")
    public ResponseEntity<?> postCodeSnippet(@RequestBody Code rqstCode) {
        //++idCounter have to be in post constructor coz error 500 ID = null
        Code nextCode = new Code(idCounter++, rqstCode.getCode(), LoadDate.getLoadDate());
        System.out.println(nextCode.getId());
        codeList.add(nextCode);
        codeService.saveOrUpdate(rqstCode);
        return ResponseEntity.ok(new DTOCode(String.valueOf(nextCode.getId())));
    }

    // API endpoint for retrieving code snippet
    @GetMapping(value = "/api/code/{id}", produces = "application/json")
    public ResponseEntity<Code> getCodeSnippet(@PathVariable Long id) {
        Code codeByID = findCodeById(id);
        return ResponseEntity.ok(codeByID);
    }

    // Browser endpoint for posting code snippet
    @GetMapping(value = "/code/new", produces = "text/html")
    public ModelAndView createCodeSnippet() {
        return new ModelAndView("create");
    }

    // Browser endpoint for retrieving code snippet
    @GetMapping(value = "/code/{id}", produces = "text/html")
    public ModelAndView getIndex(@PathVariable Long id) {
        Code codeByID = findCodeById(id);
        ModelAndView mv = new ModelAndView("index");
        mv.addObject("time", codeByID.getDate());
        mv.addObject("code", codeByID.getCode());
        return mv;
    }

    // API endpoint for retrieving 10 latest code codeSnippet
    @GetMapping(value = "/api/code/latest", produces = "application/json")
    public ResponseEntity<List<Code>> getLatestCodeSnippet() {
        List<Code> sortedList = findLatest10CodesSorted(codeList);

        return ResponseEntity.ok(sortedList);
    }

    // Browser endpoint for retrieving 10 Latest codeSnippet
    @GetMapping(value = "/code/latest", produces = "text/html")
    public ModelAndView getLatest(Code code) {
        List<Code> sortedList = findLatest10CodesSorted(codeList);
        ModelAndView mv = new ModelAndView("latest");
        mv.addObject("codes", sortedList);
        return mv;
    }

    public Code findCodeById(Long id) {

        return codeList.stream()
                .filter(code -> code.getId() == id)
                .findFirst()
                .orElseThrow(() -> new CodeNotFoundException("id:" + id)
                );
    }

    public List<Code> findLatest10CodesSorted(List<Code> codeList) {

        return codeList.stream()
                .sorted(Comparator.comparing(Code::getId).reversed())
                .limit(10)
                .collect(Collectors.toList());
    }

}