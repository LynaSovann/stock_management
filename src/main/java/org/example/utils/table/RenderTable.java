package org.example.utils.table;

import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.Table;

import java.util.List;

public class RenderTable {
    private static final CellStyle ALIGN_CENTER = new CellStyle(CellStyle.HorizontalAlign.CENTER);

    public static void renderMenu() {
        Table table = new Table(3, BorderStyle.UNICODE_ROUND_BOX);
        List<String> menus = List.of(
                "  *) Display", "  W) Write", "  R) Read",
                "  U) Update", "  D) Delete", "  S) Search",
                "  Se) Set Row", "  Sa) Save", "  Un) Unsaved",
                "  Ba) Backup", "  Re) Restore", "  E) Exit"
        );
        int[] colWd = new int[]{20, 20, 20};

        for(int i = 0; i<3; i++) table.setColumnWidth(i, colWd[i], colWd[i]);

        table.addCell("Menus", ALIGN_CENTER, 3);
        menus.forEach(table::addCell);
        System.out.println(table.render());

    }

}
