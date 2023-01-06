package com.shareNwork.resource;

import com.shareNwork.domain.processEngine.Process;
import com.shareNwork.domain.processEngine.ProcessAction;
import com.shareNwork.repository.ProcessRepository;
import com.shareNwork.service.Processhandler;
import lombok.AllArgsConstructor;
import org.eclipse.microprofile.graphql.*;

import java.text.ParseException;
import java.util.List;

@AllArgsConstructor
@GraphQLApi
public class ProcessResource {
    private ProcessRepository processRepository;
    private Processhandler processhandler;

    @Query("process")
    @Description("Get all processes")
    public List<Process> findAll() {
        return processRepository.findAll().list();
    }

    @Query("processAction")
    @Description("Get all processes action")
    public List<ProcessAction> findAllProcessActions(@Name("processId") Long processId) {
        return ProcessAction.findAll().list();
    }

    @Mutation
    @Description("Add a new process")
    public Process createProcess(Process process) throws ParseException {
        return processRepository.updateOrCreate(process);
    }

    @Mutation
    @Description("process handler")
    public Process processActionHandler(Long processActionId, Long recordId) throws ParseException {
        return processhandler.handle(processActionId, recordId);
    }
}
