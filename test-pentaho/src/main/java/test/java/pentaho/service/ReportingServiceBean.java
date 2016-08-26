package test.java.pentaho.service;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import org.pentaho.reporting.engine.classic.core.ClassicEngineBoot;
import org.pentaho.reporting.engine.classic.core.DataFactory;
import org.pentaho.reporting.engine.classic.core.MasterReport;
import org.pentaho.reporting.engine.classic.core.ReportProcessingException;
import org.pentaho.reporting.engine.classic.core.TableDataFactory;
import org.pentaho.reporting.engine.classic.core.layout.output.AbstractReportProcessor;
import org.pentaho.reporting.engine.classic.core.modules.output.pageable.base.PageableReportProcessor;
import org.pentaho.reporting.engine.classic.core.modules.output.pageable.pdf.PdfOutputProcessor;
import org.pentaho.reporting.engine.classic.core.modules.output.table.base.StreamReportProcessor;
import org.pentaho.reporting.engine.classic.core.modules.output.table.html.AllItemsHtmlPrinter;
import org.pentaho.reporting.engine.classic.core.modules.output.table.html.FileSystemURLRewriter;
import org.pentaho.reporting.engine.classic.core.modules.output.table.html.HtmlOutputProcessor;
import org.pentaho.reporting.engine.classic.core.modules.output.table.html.HtmlPrinter;
import org.pentaho.reporting.engine.classic.core.modules.output.table.html.StreamHtmlOutputProcessor;
import org.pentaho.reporting.engine.classic.core.wizard.RelationalAutoGeneratorPreProcessor;
import org.pentaho.reporting.libraries.repository.ContentLocation;
import org.pentaho.reporting.libraries.repository.DefaultNameGenerator;
import org.pentaho.reporting.libraries.repository.stream.StreamRepository;
import test.java.pentaho.model.Car;
import test.java.pentaho.model.ReportType;

import javax.ejb.Stateless;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.util.List;

@Stateless
public class ReportingServiceBean implements ReportingService {

	@Override
	public void generatePDFReport(List<Car> cars, OutputStream out) throws ReportProcessingException {
		generateReport(cars, out, ReportType.PDF);
	}

	public void generateReport(List<Car> cars, OutputStream out, ReportType reportType) throws ReportProcessingException {
		MasterReport report = new MasterReport();
		report.addPreProcessor(new RelationalAutoGeneratorPreProcessor());

		DataFactory dataFactory = getDataFactory("ReportQuery", cars);
		report.setQuery("ReportQuery");
		report.setDataFactory(dataFactory);

		// Prepare to generate the report
		AbstractReportProcessor reportProcessor = null;
		OutputStream bufferedOut = new BufferedOutputStream(out);
		try {
			// Greate the report processor for the specified output type
			switch (reportType) {
				case PDF: {
					final PdfOutputProcessor outputProcessor =
							new PdfOutputProcessor(report.getConfiguration(), bufferedOut, report.getResourceManager());
					reportProcessor = new PageableReportProcessor(report, outputProcessor);
					break;
				}
				case HTML: {
					final StreamRepository targetRepository = new StreamRepository(bufferedOut);
					final ContentLocation targetRoot = targetRepository.getRoot();
					final HtmlOutputProcessor outputProcessor = new StreamHtmlOutputProcessor(report.getConfiguration());
					final HtmlPrinter printer = new AllItemsHtmlPrinter(report.getResourceManager());
					printer.setContentWriter(targetRoot, new DefaultNameGenerator(targetRoot, "index", "html"));
					printer.setDataWriter(null, null);
					printer.setUrlRewriter(new FileSystemURLRewriter());
					outputProcessor.setPrinter(printer);
					reportProcessor = new StreamReportProcessor(report, outputProcessor);
					break;
				}
			}

			// Generate the report
			reportProcessor.processReport();
		} finally {
			if (reportProcessor != null) {
				reportProcessor.close();
			}
		}
	}

	private DataFactory getDataFactory(String queryName, final List<Car> cars) {
		TableModel tableModel = new AbstractTableModel() {
			private final List<Object[]> dataList = FluentIterable.from(cars).transform(new Function<Car, Object[]>() {
				@Override
				public Object[] apply(Car car) {
					return car.toObjects();
				}
			}).toList();
			public int getColumnCount() {
				return dataList.get(0).length;
			}

			public int getRowCount() {
				return dataList.size();
			}

			/**
			 * Returns the data value at the specific row and column index
			 */
			public Object getValueAt(int rowIndex, int columnIndex) {
				if (rowIndex >= 0 && rowIndex < dataList.size() && columnIndex >= 0 && columnIndex < dataList.get(rowIndex).length) {
					return dataList.get(rowIndex)[columnIndex];
				}
				return null;
			}
		};

		return new TableDataFactory(queryName, tableModel);
	}
}
