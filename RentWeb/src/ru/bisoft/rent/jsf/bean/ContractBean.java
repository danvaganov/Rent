package ru.bisoft.rent.jsf.bean;

import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.event.FlowEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import ru.bisoft.rent.ejb.dao.ContractEJB;
import ru.bisoft.rent.model.Article.ArticleState;
import ru.bisoft.rent.model.Contract;
import ru.bisoft.rent.model.Contract.ContractState;
import ru.bisoft.rent.model.ContractArticle;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

public class ContractBean extends LazyDataModel<Contract> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Configuration cfg = new Configuration(Configuration.VERSION_2_3_22);

	@EJB
	ContractEJB contractEJB;

	LoginBean loginBean;

	private Contract selection;

	@Override
	public List<Contract> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
		return contractEJB.find(loginBean.gettUser().getEmployee().getOrganization(), first, pageSize, filters);
	}

	@Override
	public int getRowCount() {
		return contractEJB.getCountByOrganization(loginBean.gettUser().getEmployee().getOrganization()).intValue();
	}

	@Override
	public Object getRowKey(Contract object) {
		return Integer.valueOf(object.getKeyContract());
	}

	@Override
	public Contract getRowData(String rowKey) {
		return contractEJB.findById(Integer.valueOf(rowKey));
	}

	public Contract getSelection() {
		return selection;
	}

	public void setSelection(Contract selection) {
		this.selection = selection;
	}

	public void delete(Contract contract) {
		FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Удаление договора", contract.toString()));
		if (contract.getStateContract() == ContractState.PROCESS)
			for (ContractArticle contractArticle : contract.getContractArticles())
				contractArticle.getArticle().setStateArticle(ArticleState.IN_STOCK);
		contractEJB.update(contract);
		contractEJB.delete(contract.getKeyContract());
	}

	public void close(Contract contract) {
		if (contract.getStateContract() != ContractState.CLOSE)
		{
			for (ContractArticle contractArticle : contract.getContractArticles())
				if (contractArticle.getArticle().getStateArticle() == ArticleState.IN_RENT)
					contractArticle.getArticle().setStateArticle(ArticleState.IN_STOCK);
			contract.setStateContract(ContractState.CLOSE);
			contractEJB.update(contract);
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Закрытие договора", contract.toString()));
		}
		else
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Договор уже закрыт", ""));
	}

	public LoginBean getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}

	public void prepareInsert() {
		selection = new Contract(contractEJB.getNumberContract(loginBean.gettUser().getEmployee().getOrganization()).toString());
		selection.setOrganization(loginBean.gettUser().getEmployee().getOrganization());
		selection.setClosedContract(false);
		selection.setCreateContract(new Date());
		selection.setBeginContract(new Date());
		selection.setStateContract(ContractState.OPEN);
		selection.setContractArticles(new ArrayList<ContractArticle>());
	}

	public void update() {
		if (selection.getStateContract() == ContractState.OPEN)
		{
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Создание договора", selection.toString()));
			for (ContractArticle contractArticle : selection.getContractArticles())
			{
				contractArticle.getArticle().setStateArticle(ArticleState.IN_RENT);
				contractArticle.getArticle().incRate();
			}
			selection.setStateContract(ContractState.PROCESS);
		}
		contractEJB.update(selection);
	}

	public String onFlowProcess(FlowEvent event) {
		return event.getNewStep();
	}

	public void showContract(Contract contract) {
		OutputStreamWriter out;
		Map<String, Object> root = new HashMap<>();
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		HttpServletResponse httpServletResponse = (HttpServletResponse) externalContext.getResponse();
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
		Template template;
		try {
			template = new Template("contractTemplate", new StringReader(loginBean.gettUser().getEmployee().getOrganization().getContractTemplateOrganization()), cfg);
			out = new OutputStreamWriter(httpServletResponse.getOutputStream());
			httpServletResponse.addHeader("Content-Type", "text/html");

			root.put("contract", contract);
			
			template.process(root, out);
			out.flush();
			context.responseComplete();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void showRequest(Contract contract) {
		OutputStreamWriter out;
		Map<String, Object> root = new HashMap<>();
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		HttpServletResponse httpServletResponse = (HttpServletResponse) externalContext.getResponse();
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
		Template template;
		try {
			template = new Template("requestTemplate", new StringReader(loginBean.gettUser().getEmployee().getOrganization().getRequestTemplateOrganization()), cfg);
			out = new OutputStreamWriter(httpServletResponse.getOutputStream());
			httpServletResponse.addHeader("Content-Type", "text/html");

			root.put("contract", contract);
			
			template.process(root, out);
			out.flush();
			context.responseComplete();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ContractState[] getStatuses()
	{
		return ContractState.values();
	}
	
	@PostConstruct
	public void postConstruct()
	{
		contractEJB.updateOutOfDate(loginBean.gettUser().getEmployee().getOrganization());
	}
}
