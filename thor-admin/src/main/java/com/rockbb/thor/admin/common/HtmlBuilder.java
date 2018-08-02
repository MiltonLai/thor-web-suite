package com.rockbb.thor.admin.common;

public class HtmlBuilder
{
    public static String generate_pagination_dropdown(String base_url, String param, int num_items, int per_page, int start_item, boolean add_prevnext_text, String tpl_prefix)
    {
        // Make sure per_page is a valid value
        per_page = (per_page <= 0) ? 10 : per_page;
        int total_pages = num_items / per_page + ((num_items % per_page > 0) ? 1 : 0); // Instead of the ceil method

        int on_page = start_item / per_page + 1;
        String url_delim = (base_url.indexOf("?") > 0) ? "&amp;" : "?";

        StringBuilder html_sb = new StringBuilder();
        html_sb.append("<span>");
        html_sb.append((on_page <= 1) ? "" : "<a href=\"" + base_url + url_delim + param + "=" + ((on_page - 2) * per_page) + "\">&lt;&lt;</a>");
        html_sb.append(" " + on_page + "/" + total_pages + " (" + num_items + ") ");
        html_sb.append((on_page == total_pages) ? "" : "<a href=\"" + base_url + url_delim + param + "=" + (on_page * per_page) + "\">&gt;&gt;</a>");
        if (total_pages > 3) {
            String js_bas_url = "'" + base_url + url_delim + param + "='";
            html_sb.append(" <select name=\"pagination_dropdown\" id=\"pagination_dropdown\" onchange=\"flip_page('pagination_dropdown', " + js_bas_url + ")\">");
            for (int i = 1; i <= total_pages; i++) {
                html_sb.append("<option value=\"" + (i - 1) * per_page + "\"" + ((i == on_page) ? " selected" : "") + ">" + i + "</option>");
            }
            html_sb.append("</select>");
        }

        return html_sb.toString();
    }

    /**
     * Pagination routine, generates page number sequence
     * tpl_prefix is for using different pagination blocks at one page
     * <p/>
     * <ul class="pagination">
     * <li><a href="#">&laquo;</a></li>
     * <li><a href="#">1</a></li>
     * <li><a href="#">2</a></li>
     * <li><a href="#">3</a></li>
     * <li><a href="#">4</a></li>
     * <li><a href="#">5</a></li>
     * <li><a href="#">&raquo;</a></li>
     * </ul>
     */
    public static String pagination(
            String base_url,
            String extension,
            long total,
            int page_size,
            int on_page,
            boolean add_prevnext_text,
            boolean add_count)
    {
        // Make sure page size is a valid value
        page_size = (page_size <= 0) ? 1 : page_size;

        String seperater = " ";
        String current_pre = "\n<li class=\"active\"><span>";
        String current_pos = "</span></li>";
        long total_pages = total / page_size + ((total % page_size > 0) ? 1 : 0); // Instead of the ceil method

        if (total_pages == 1 || total == 0) {
            StringBuilder page_string = new StringBuilder();
            if (add_count) {
                page_string.append("<ul class=\"pagination\">");
                page_string.append("<li><span class=\"count\">").append(total).append("</span></li>");
                page_string.append("</ul>");
            }
            return page_string.toString();
        }
        if (on_page < 1) on_page = 1;

        String url_delim = "-";

        StringBuilder page_string = new StringBuilder();
        page_string.append("<ul class=\"pagination\">");
        if (add_prevnext_text) {
            if (on_page != 1) {
                page_string.append("\n<li><a href=\"" + base_url + url_delim + (on_page - 1) + extension + "\">&laquo;</a></li>");
            }
        }
        page_string.append((on_page == 1) ? current_pre + "1" + current_pos : "\n<li><a href=\"" + base_url + url_delim + 1 + extension + "\">1</a></li>");

        if (total_pages > 5) {
            long start_cnt;
            if (on_page - 4 > 1) {
                start_cnt = on_page - 4;
            } else {
                start_cnt = 1;
            }
            if (total_pages - 5 < start_cnt) {
                start_cnt = total_pages - 5;
            }

            long end_cnt;
            if (on_page + 4 > total_pages) {
                end_cnt = total_pages;
            } else {
                end_cnt = on_page + 4;
            }
            if (end_cnt < 6) {
                end_cnt = 6;
            }

            page_string.append((start_cnt > 1) ? "\n<li><span>...</span></li>" : seperater);

            for (long i = start_cnt + 1; i < end_cnt; i++) {
                page_string.append((i == on_page) ? current_pre + i + current_pos :
                        "\n<li><a href=\"" + base_url + url_delim + i + extension + "\">" + i + "</a></li>");
                if (i < end_cnt - 1) {
                    page_string.append(seperater);
                }
            }
            page_string.append((end_cnt < total_pages) ? "\n<li><span>...</span></li>" : seperater);

        } else {
            page_string.append(seperater);

            for (int i = 2; i < total_pages; i++) {
                page_string.append((i == on_page) ? current_pre + i + current_pos :
                        "\n<li><a href=\"" + base_url + url_delim + i + extension + "\">" + i + "</a></li>");
                if (i < total_pages) {
                    page_string.append(seperater);
                }
            }
        }

        page_string.append((on_page == total_pages) ? current_pre + total_pages + current_pos :
                "\n<li><a href=\"" + base_url + url_delim + total_pages + extension + "\">" + total_pages + "</a></li>");

        if (add_prevnext_text) {
            if (on_page != total_pages) {
                page_string.append("\n<li><a href=\"" + base_url + url_delim + (on_page + 1) + extension + "\">&raquo;</a></li>");
            }
        }

        if (add_count) {
            page_string.append("\n<li><span class=\"count\">").append(total).append("</span></li>");
        }
        page_string.append("\n</ul>");

        return page_string.toString();
    }

    /* Test cases
    *
    * @param args
    */
    public static void main(String[] args)
    {
        String out = pagination("aa-bb-1-2", ".html", 100, 10, 7, true, true);
        System.out.println(out);
    }

}
