package JavaCA.service;

import java.util.List;
import java.util.Map;

import JavaCA.model.Product;

public interface ReportService 
{
	Map<Double, List<List<Product>>> reorderReportData();
}
