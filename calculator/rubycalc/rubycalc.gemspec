
Gem::Specification.new do |s|
  s.name    = 'rubycalc'
  s.version = '0.0.2'
  s.date    = '2016-05-11'
  s.summary = 'Another calculator in ruby'
  s.description = 'An implementation of a basic calculator on ruby'
  s.author  = 'Kent D. Lee - Hamilton Tobon Mosquera'
  s.email   = 'htobonm@eafit.edu.co'
  s.homepage = 'http://www1.eafit.edu.co/fcardona/cursos/st0244/rubycal'
  s.files    = ["lib/token.rb",
                "lib/scanner.rb",
                "lib/ast.rb",
                "lib/parser.rb",
                "lib/calculator.rb",
                "lib/calcex.rb"]
  s.license  = 'ARTISTIC'
  s.executables << 'rubycalc'
end
