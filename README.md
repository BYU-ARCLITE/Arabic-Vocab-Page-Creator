# Arabic Vocab Page Creator
Turns excel files into vocab .html pages. It needs four pieces of information: Category, Arabic text, English text, and URL. Each entry needs to be its own row. Each sheet in the excel file is a different page with the name of the sheet being the name of the page.

## Setup
This is an sbt project, so you need sbt installed. http://www.scala-sbt.org/

## Running
Run sbt from the console in the project directory. Then compile the code:

    > compile

To run, just run from sbt:

    > run

You can specify the input file (assuming it is under <code>src/main/resources</code>):

    > run otherfile.xlsx

You can also define the order of the columns. The input file must be specified:

    > run otherfile.xlsx "arabic url english category"

## Output
This writes .html files to the <code>src/main/resources</code> directory.