package com.shareNwork.repository;

import java.text.ParseException;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;

import com.shareNwork.domain.processEngine.Process;
import com.shareNwork.domain.processEngine.ProcessAction;
import com.shareNwork.domain.processEngine.ProcessField;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class ProcessRepository implements PanacheRepository<Process> {

    @Inject
    EntityManager em;

    @Transactional
    public Process updateOrCreate(Process process) throws ParseException {
        if (process.id == null) {
            persist(process);
            if (process.getProcessActions() != null) {
                addActionsToProcess(process.id, process.getProcessActions());
            }
            if (process.getProcessFields() != null) {
                addFieldsToProcess(process.id, process.getProcessFields());
            }
            return process;
        } else {
            addActionsToProcess(process.id, process.getProcessActions());
            return em.merge(process);
        }
    }

    @Transactional
    public Process addActionsToProcess(Long id, List<ProcessAction> processActions) throws ParseException {
        Process employee = findById(id);
        if (employee == null) {
            throw new NotFoundException();
        } else {
            for (ProcessAction employeeSkillProficiency : processActions) {
                if (employeeSkillProficiency.id != null) {
                    updateProcessActions(employeeSkillProficiency.id, employeeSkillProficiency);
                } else {
                    employeeSkillProficiency.setProcess(employee);
                    employeeSkillProficiency.persist();
                }
            }
        }
        return employee;
    }

    @Transactional
    public ProcessAction updateProcessActions(Long id, ProcessAction employeeSkillProficiency) throws ParseException {
        ProcessAction employeeSkillProficiency1 = ProcessAction.findById(employeeSkillProficiency.id);
        if (employeeSkillProficiency1 == null) {
            throw new NotFoundException();
        }
        if (employeeSkillProficiency.getActionName() != null) {
            employeeSkillProficiency1.setActionName(employeeSkillProficiency.getActionName());
        }

        employeeSkillProficiency1.persist();
        return employeeSkillProficiency1;
    }

    @Transactional
    public Process addFieldsToProcess(Long id, List<ProcessField> processActions) throws ParseException {
        Process employee = findById(id);
        if (employee == null) {
            throw new NotFoundException();
        } else {
            for (ProcessField employeeSkillProficiency : processActions) {
                if (employeeSkillProficiency.id != null) {
                    updateProcessFields(employeeSkillProficiency.id, employeeSkillProficiency);
                } else {
                    employeeSkillProficiency.setProcess(employee);
                    employeeSkillProficiency.persist();
                }
            }
        }
        return employee;
    }

    @Transactional
    public ProcessField updateProcessFields(Long id, ProcessField employeeSkillProficiency) throws ParseException {
        ProcessField employeeSkillProficiency1 = ProcessField.findById(employeeSkillProficiency.id);
        if (employeeSkillProficiency1 == null) {
            throw new NotFoundException();
        }
        if (employeeSkillProficiency.getFieldName() != null) {
            employeeSkillProficiency1.setFieldName(employeeSkillProficiency.getFieldName());
        }
        if (employeeSkillProficiency.getFieldType() != null) {
            employeeSkillProficiency1.setFieldType(employeeSkillProficiency.getFieldType());
        }
        employeeSkillProficiency1.persist();
        return employeeSkillProficiency1;
    }
}
