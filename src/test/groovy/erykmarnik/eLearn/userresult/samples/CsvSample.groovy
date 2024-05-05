package erykmarnik.eLearn.userresult.samples

trait CsvSample {
  def createUserResultCsvData(List<String>... changes) {
    List<List<String>> csvData = new ArrayList<>()
    List<List<String>> resultCsvData = changes.collect(row ->
        row.collect(cell -> cell.replaceAll(/\[|]/, '')))
    csvData.addAll(resultCsvData)
    csvData.collect(row -> row.join(',')).join('\n')
  }
}