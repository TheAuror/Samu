all:	clean validate_xmllint_idea_hu samuentropy-idea-hu.pdf

samuentropy-idea-hu.pdf: samuentropy-idea-hu.xml samuentropy-hu.xls
	dblatex samuentropy-idea-hu.xml -p samuentropy-hu.xls

.PHONY: validate_jing_idea_hu
validate_jing_idea_hu:
	xmllint --xinclude samuentropy-idea-hu.xml --output output.xml
	java -jar /home/nbatfai/Downloads/jing-trang-20131210/build/jing.jar -c http://docbook.org/xml/5.1CR4/rng/docbookxi.rnc output.xml

.PHONY: validate_xmllint_idea_hu
validate_xmllint_idea_hu:
	xmllint --xinclude samuentropy-idea-hu.xml --output output.xml
	xmllint --relaxng http://docbook.org/xml/5.1CR4/rng/docbookxi.rng output.xml --noout
	rm -f output.xml

.PHONY: clean
clean:
	rm -vf samuentropy-idea-hu.pdf 
