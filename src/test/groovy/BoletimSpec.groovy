import spock.lang.Specification

import java.nio.charset.StandardCharsets

class BoletimSpec extends Specification {
    def "includes the first grade when calculating the average"() {
        expect:
        runBoletim().contains("Ana: média 8.17 - Aprovado")
    }

    def "classifies an average of 6.00 as recovery"() {
        expect:
        runBoletim().contains("Bruno: média 6.00 - Recuperação")
    }

    def "approves students with an average exactly equal to 7.00"() {
        expect:
        def lines = runBoletim()
        lines.contains("Carla: média 7.00 - Aprovado")
        lines.contains("Diego: média 7.00 - Aprovado")
    }

    private List<String> runBoletim() {
        def originalOut = System.out
        def output = new ByteArrayOutputStream()

        try {
            System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8))
            new Boletim().run()
        } finally {
            System.setOut(originalOut)
        }

        output.toString(StandardCharsets.UTF_8).readLines()
    }
}
