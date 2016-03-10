package ru.bisoft.rent.jsf.bean;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;

import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

import ru.bisoft.rent.ejb.dao.ReportEJB;
import ru.bisoft.rent.model.StatisticReport;

public class ReportBean {
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	@EJB
	ReportEJB reportEJB;
	
	LoginBean loginBean;
	
	private BarChartModel barChartModel;
	
	public List<StatisticReport> getStatistic()
	{
		return null;
		//return reportEJB.getStatistic();
	}
	
	@PostConstruct
	public void init ()
	{

	}

	public BarChartModel getBarChartModel() {
		barChartModel = new BarChartModel();
        
		barChartModel.setTitle("Bar Chart");
		barChartModel.setLegendPosition("ne");
        
        ChartSeries contractsChartSeries = new ChartSeries();
        ChartSeries closedChartSeries = new ChartSeries();
     
        contractsChartSeries.setLabel("Договора созданы");

        List<Object[]> list;
        list= reportEJB.getCreatedContract();
        for (Object[] object: list)
        	contractsChartSeries.set(object[0], (Long)object[1]);
        
        closedChartSeries.setLabel("Договора закрыты");
        list= reportEJB.getClosedContract();
        for (Object[] object: list)
        	closedChartSeries.set(object[0], (Long)object[1]);

        barChartModel.addSeries(contractsChartSeries);
        barChartModel.addSeries(closedChartSeries);
		return barChartModel;
	}

	public void setBarChartModel(BarChartModel barChartModel) {
		this.barChartModel = barChartModel;
	}

	public LoginBean getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}
}
