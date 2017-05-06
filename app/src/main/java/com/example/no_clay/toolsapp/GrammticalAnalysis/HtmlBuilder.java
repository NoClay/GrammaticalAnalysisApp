package com.example.no_clay.toolsapp.GrammticalAnalysis;

/**
 * Created by noclay on 2017/5/4.
 */

public class HtmlBuilder {
    private static final String HEAD = "<!doctype html>\n" +
            "<html>\n" +
            "<head>\n" +
            "<meta charset='UTF-8'><meta name='viewport' content='width=device-width initial-scale=1'>\n" +
            "<title></title><link href='http://fonts.googleapis.com/css?family=Open+Sans:400italic,700italic,700,400&subset=latin,latin-ext' rel='stylesheet' type='text/css' /><style type='text/css'>html {overflow-x: initial !important;}html { font-size: 14px; background-color: rgb(255, 255, 255); color: rgb(51, 51, 51); }\n" +
            "body { margin: 0px; padding: 0px; height: auto; bottom: 0px; top: 0px; left: 0px; right: 0px; font-family: \"Helvetica Neue\", Helvetica, Arial, sans-serif; font-size: 1rem; line-height: 1.42857; overflow-x: hidden; background: inherit; }\n" +
            "a:active, a:hover { outline: 0px; }\n" +
            ".in-text-selection, ::selection { background: rgb(181, 214, 252); text-shadow: none; }\n" +
            "#write { margin: 0px auto; height: auto; width: inherit; word-break: normal; word-wrap: break-word; position: relative; padding-bottom: 70px; white-space: pre-wrap; overflow-x: visible; }\n" +
            ".for-image #write { padding-left: 8px; padding-right: 8px; }\n" +
            "body.typora-export { padding-left: 30px; padding-right: 30px; }\n" +
            "@media screen and (max-width: 500px) {\n" +
            "  body.typora-export { padding-left: 0px; padding-right: 0px; }\n" +
            "  .CodeMirror-sizer { margin-left: 0px !important; }\n" +
            "  .CodeMirror-gutters { display: none !important; }\n" +
            "}\n" +
            ".typora-export #write { margin: 0px auto; }\n" +
            "#write > p:first-child, #write > ul:first-child, #write > ol:first-child, #write > pre:first-child, #write > blockquote:first-child, #write > div:first-child, #write > table:first-child { margin-top: 30px; }\n" +
            "#write li > table:first-child { margin-top: -20px; }\n" +
            "img { max-width: 100%; vertical-align: middle; }\n" +
            "input, button, select, textarea { color: inherit; font-style: inherit; font-variant: inherit; font-weight: inherit; font-stretch: inherit; font-size: inherit; line-height: inherit; font-family: inherit; }\n" +
            "input[type=\"checkbox\"], input[type=\"radio\"] { line-height: normal; padding: 0px; }\n" +
            "::before, ::after, * { box-sizing: border-box; }\n" +
            "#write p, #write h1, #write h2, #write h3, #write h4, #write h5, #write h6, #write div, #write pre { width: inherit; }\n" +
            "#write p, #write h1, #write h2, #write h3, #write h4, #write h5, #write h6 { position: relative; }\n" +
            "h1 { font-size: 2rem; }\n" +
            "h2 { font-size: 1.8rem; }\n" +
            "h3 { font-size: 1.6rem; }\n" +
            "h4 { font-size: 1.4rem; }\n" +
            "h5 { font-size: 1.2rem; }\n" +
            "h6 { font-size: 1rem; }\n" +
            "p { -webkit-margin-before: 1rem; -webkit-margin-after: 1rem; -webkit-margin-start: 0px; -webkit-margin-end: 0px; }\n" +
            ".mathjax-block { margin-top: 0px; margin-bottom: 0px; -webkit-margin-before: 0rem; -webkit-margin-after: 0rem; }\n" +
            ".hidden { display: none; }\n" +
            ".md-blockmeta { color: rgb(204, 204, 204); font-weight: bold; font-style: italic; }\n" +
            "a { cursor: pointer; }\n" +
            "sup.md-footnote { padding: 2px 4px; background-color: rgba(238, 238, 238, 0.701961); color: rgb(85, 85, 85); border-radius: 4px; }\n" +
            "#write input[type=\"checkbox\"] { cursor: pointer; width: inherit; height: inherit; margin: 4px 0px 0px; }\n" +
            "tr { break-inside: avoid; break-after: auto; }\n" +
            "thead { display: table-header-group; }\n" +
            "table { border-collapse: collapse; border-spacing: 0px; width: 100%; overflow: auto; break-inside: auto; text-align: left; }\n" +
            "table.md-table td { min-width: 80px; }\n" +
            ".CodeMirror-gutters { border-right: 0px; background-color: inherit; }\n" +
            ".CodeMirror { text-align: left; }\n" +
            ".CodeMirror-placeholder { opacity: 0.3; }\n" +
            ".CodeMirror pre { padding: 0px 4px; }\n" +
            ".CodeMirror-lines { padding: 0px; }\n" +
            "div.hr:focus { cursor: none; }\n" +
            "pre { white-space: pre-wrap; }\n" +
            ".CodeMirror-gutters { margin-right: 4px; }\n" +
            ".md-fences { font-size: 0.9rem; display: block; break-inside: avoid; text-align: left; overflow: visible; white-space: pre; background: inherit; position: relative !important; }\n" +
            ".md-diagram-panel { width: 100%; margin-top: 10px; text-align: center; padding-top: 0px; padding-bottom: 8px; overflow-x: auto; }\n" +
            ".md-fences .CodeMirror.CodeMirror-wrap { top: -1.6em; margin-bottom: -1.6em; }\n" +
            ".md-fences.mock-cm { white-space: pre-wrap; }\n" +
            ".show-fences-line-number .md-fences { padding-left: 0px; }\n" +
            ".show-fences-line-number .md-fences.mock-cm { padding-left: 40px; }\n" +
            ".footnotes { opacity: 0.8; font-size: 0.9rem; padding-top: 1em; padding-bottom: 1em; }\n" +
            ".footnotes + .footnotes { margin-top: -1em; }\n" +
            ".md-reset { margin: 0px; padding: 0px; border: 0px; outline: 0px; vertical-align: top; background: transparent; text-decoration: none; text-shadow: none; float: none; position: static; width: auto; height: auto; white-space: nowrap; cursor: inherit; -webkit-tap-highlight-color: transparent; line-height: normal; font-weight: normal; text-align: left; box-sizing: content-box; direction: ltr; }\n" +
            "li div { padding-top: 0px; }\n" +
            "blockquote { margin: 1rem 0px; }\n" +
            "li p, li .mathjax-block { margin: 0.5rem 0px; }\n" +
            "li { margin: 0px; position: relative; }\n" +
            "blockquote > :last-child { margin-bottom: 0px; }\n" +
            "blockquote > :first-child { margin-top: 0px; }\n" +
            ".footnotes-area { color: rgb(136, 136, 136); margin-top: 0.714rem; padding-bottom: 0.143rem; }\n" +
            "@media print {\n" +
            "  html, body { height: 100%; }\n" +
            "  .typora-export * { -webkit-print-color-adjust: exact; }\n" +
            "  h1, h2, h3, h4, h5, h6 { break-after: avoid-page; orphans: 2; }\n" +
            "  p { orphans: 4; }\n" +
            "  html.blink-to-pdf { font-size: 13px; }\n" +
            "  .typora-export #write { padding-left: 1cm; padding-right: 1cm; }\n" +
            "  .typora-export #write::after { height: 0px; }\n" +
            "  @page { margin: 20mm 0mm; }\n" +
            "}\n" +
            ".footnote-line { margin-top: 0.714em; font-size: 0.7em; }\n" +
            "a img, img a { cursor: pointer; }\n" +
            "pre.md-meta-block { font-size: 0.8rem; min-height: 2.86rem; white-space: pre-wrap; background: rgb(204, 204, 204); display: block; overflow-x: hidden; }\n" +
            "p .md-image:only-child { display: inline-block; width: 100%; text-align: center; }\n" +
            "#write .MathJax_Display { margin: 0.8em 0px 0px; }\n" +
            ".mathjax-block { white-space: pre; overflow: hidden; width: 100%; }\n" +
            "p + .mathjax-block { margin-top: -1.143rem; }\n" +
            ".mathjax-block:not(:empty)::after { display: none; }\n" +
            "[contenteditable=\"true\"]:active, [contenteditable=\"true\"]:focus { outline: none; box-shadow: none; }\n" +
            ".task-list { list-style-type: none; }\n" +
            ".task-list-item { position: relative; padding-left: 1em; }\n" +
            ".task-list-item input { position: absolute; top: 0px; left: 0px; }\n" +
            ".math { font-size: 1rem; }\n" +
            ".md-toc { min-height: 3.58rem; position: relative; font-size: 0.9rem; border-radius: 10px; }\n" +
            ".md-toc-content { position: relative; margin-left: 0px; }\n" +
            ".md-toc::after, .md-toc-content::after { display: none; }\n" +
            ".md-toc-item { display: block; color: rgb(65, 131, 196); text-decoration: none; }\n" +
            ".md-toc-inner:hover { }\n" +
            ".md-toc-inner { display: inline-block; cursor: pointer; }\n" +
            ".md-toc-h1 .md-toc-inner { margin-left: 0px; font-weight: bold; }\n" +
            ".md-toc-h2 .md-toc-inner { margin-left: 2em; }\n" +
            ".md-toc-h3 .md-toc-inner { margin-left: 4em; }\n" +
            ".md-toc-h4 .md-toc-inner { margin-left: 6em; }\n" +
            ".md-toc-h5 .md-toc-inner { margin-left: 8em; }\n" +
            ".md-toc-h6 .md-toc-inner { margin-left: 10em; }\n" +
            "@media screen and (max-width: 48em) {\n" +
            "  .md-toc-h3 .md-toc-inner { margin-left: 3.5em; }\n" +
            "  .md-toc-h4 .md-toc-inner { margin-left: 5em; }\n" +
            "  .md-toc-h5 .md-toc-inner { margin-left: 6.5em; }\n" +
            "  .md-toc-h6 .md-toc-inner { margin-left: 8em; }\n" +
            "}\n" +
            "a.md-toc-inner { font-size: inherit; font-style: inherit; font-weight: inherit; line-height: inherit; }\n" +
            ".footnote-line a:not(.reversefootnote) { color: inherit; }\n" +
            ".md-attr { display: none; }\n" +
            ".md-fn-count::after { content: \".\"; }\n" +
            ".md-tag { opacity: 0.5; }\n" +
            ".md-comment { color: rgb(162, 127, 3); opacity: 0.8; font-family: monospace; }\n" +
            "code { text-align: left; }\n" +
            "h1 .md-tag, h2 .md-tag, h3 .md-tag, h4 .md-tag, h5 .md-tag, h6 .md-tag { font-weight: initial; opacity: 0.35; }\n" +
            "a.md-print-anchor { border-width: initial !important; border-style: none !important; border-color: initial !important; display: inline-block !important; position: absolute !important; width: 1px !important; right: 0px !important; outline: none !important; background: transparent !important; text-decoration: initial !important; text-shadow: initial !important; }\n" +
            ".md-inline-math .MathJax_SVG .noError { display: none !important; }\n" +
            ".mathjax-block .MathJax_SVG_Display { text-align: center; margin: 1em 0em; position: relative; text-indent: 0px; max-width: none; max-height: none; min-height: 0px; min-width: 100%; width: auto; display: block !important; }\n" +
            ".MathJax_SVG_Display, .md-inline-math .MathJax_SVG_Display { width: auto; margin: inherit; display: inline-block !important; }\n" +
            ".MathJax_SVG .MJX-monospace { font-family: monospace; }\n" +
            ".MathJax_SVG .MJX-sans-serif { font-family: sans-serif; }\n" +
            ".MathJax_SVG { display: inline; font-style: normal; font-weight: normal; line-height: normal; zoom: 90%; text-indent: 0px; text-align: left; text-transform: none; letter-spacing: normal; word-spacing: normal; word-wrap: normal; white-space: nowrap; float: none; direction: ltr; max-width: none; max-height: none; min-width: 0px; min-height: 0px; border: 0px; padding: 0px; margin: 0px; }\n" +
            ".MathJax_SVG * { transition: none; }\n" +
            "\n" +
            "\n" +
            "@font-face { font-family: \"Open Sans\"; font-style: normal; font-weight: normal; src: local(\"Open Sans Regular\"), url(\"./github/400.woff\") format(\"woff\"); }\n" +
            "@font-face { font-family: \"Open Sans\"; font-style: italic; font-weight: normal; src: local(\"Open Sans Italic\"), url(\"./github/400i.woff\") format(\"woff\"); }\n" +
            "@font-face { font-family: \"Open Sans\"; font-style: normal; font-weight: bold; src: local(\"Open Sans Bold\"), url(\"./github/700.woff\") format(\"woff\"); }\n" +
            "@font-face { font-family: \"Open Sans\"; font-style: italic; font-weight: bold; src: local(\"Open Sans Bold Italic\"), url(\"./github/700i.woff\") format(\"woff\"); }\n" +
            "html { font-size: 16px; }\n" +
            "body { font-family: \"Open Sans\", \"Clear Sans\", \"Helvetica Neue\", Helvetica, Arial, sans-serif; color: rgb(51, 51, 51); line-height: 1.6; }\n" +
            "#write { max-width: 860px; margin: 0px auto; padding: 20px 30px 100px; }\n" +
            "#write > ul:first-child, #write > ol:first-child { margin-top: 30px; }\n" +
            "body > :first-child { margin-top: 0px !important; }\n" +
            "body > :last-child { margin-bottom: 0px !important; }\n" +
            "a { color: rgb(65, 131, 196); }\n" +
            "h1, h2, h3, h4, h5, h6 { position: relative; margin-top: 1rem; margin-bottom: 1rem; font-weight: bold; line-height: 1.4; cursor: text; }\n" +
            "h1:hover a.anchor, h2:hover a.anchor, h3:hover a.anchor, h4:hover a.anchor, h5:hover a.anchor, h6:hover a.anchor { text-decoration: none; }\n" +
            "h1 tt, h1 code { font-size: inherit; }\n" +
            "h2 tt, h2 code { font-size: inherit; }\n" +
            "h3 tt, h3 code { font-size: inherit; }\n" +
            "h4 tt, h4 code { font-size: inherit; }\n" +
            "h5 tt, h5 code { font-size: inherit; }\n" +
            "h6 tt, h6 code { font-size: inherit; }\n" +
            "h1 { padding-bottom: 0.3em; font-size: 2.25em; line-height: 1.2; border-bottom: 1px solid rgb(238, 238, 238); }\n" +
            "h2 { padding-bottom: 0.3em; font-size: 1.75em; line-height: 1.225; border-bottom: 1px solid rgb(238, 238, 238); }\n" +
            "h3 { font-size: 1.5em; line-height: 1.43; }\n" +
            "h4 { font-size: 1.25em; }\n" +
            "h5 { font-size: 1em; }\n" +
            "h6 { font-size: 1em; color: rgb(119, 119, 119); }\n" +
            "p, blockquote, ul, ol, dl, table { margin: 0.8em 0px; }\n" +
            "li > ol, li > ul { margin: 0px; }\n" +
            "hr { height: 4px; padding: 0px; margin: 16px 0px; background-color: rgb(231, 231, 231); border-width: 0px 0px 1px; border-style: none none solid; border-top-color: initial; border-right-color: initial; border-left-color: initial; border-image: initial; overflow: hidden; box-sizing: content-box; border-bottom-color: rgb(221, 221, 221); }\n" +
            "body > h2:first-child { margin-top: 0px; padding-top: 0px; }\n" +
            "body > h1:first-child { margin-top: 0px; padding-top: 0px; }\n" +
            "body > h1:first-child + h2 { margin-top: 0px; padding-top: 0px; }\n" +
            "body > h3:first-child, body > h4:first-child, body > h5:first-child, body > h6:first-child { margin-top: 0px; padding-top: 0px; }\n" +
            "a:first-child h1, a:first-child h2, a:first-child h3, a:first-child h4, a:first-child h5, a:first-child h6 { margin-top: 0px; padding-top: 0px; }\n" +
            "h1 p, h2 p, h3 p, h4 p, h5 p, h6 p { margin-top: 0px; }\n" +
            "li p.first { display: inline-block; }\n" +
            "ul, ol { padding-left: 30px; }\n" +
            "ul:first-child, ol:first-child { margin-top: 0px; }\n" +
            "ul:last-child, ol:last-child { margin-bottom: 0px; }\n" +
            "blockquote { border-left: 4px solid rgb(221, 221, 221); padding: 0px 15px; color: rgb(119, 119, 119); }\n" +
            "blockquote blockquote { padding-right: 0px; }\n" +
            "table { padding: 0px; word-break: initial; }\n" +
            "table tr { border-top: 1px solid rgb(204, 204, 204); background-color: white; margin: 0px; padding: 0px; }\n" +
            "table tr:nth-child(2n) { background-color: rgb(248, 248, 248); }\n" +
            "table tr th { font-weight: bold; border: 1px solid rgb(204, 204, 204); text-align: left; margin: 0px; padding: 6px 13px; }\n" +
            "table tr td { border: 1px solid rgb(204, 204, 204); text-align: left; margin: 0px; padding: 6px 13px; }\n" +
            "table tr th:first-child, table tr td:first-child { margin-top: 0px; }\n" +
            "table tr th:last-child, table tr td:last-child { margin-bottom: 0px; }\n" +
            ".CodeMirror-gutters { border-right: 1px solid rgb(221, 221, 221); }\n" +
            ".md-fences, code, tt { border: 1px solid rgb(221, 221, 221); background-color: rgb(248, 248, 248); border-radius: 3px; font-family: Consolas, \"Liberation Mono\", Courier, monospace; padding: 2px 4px 0px; font-size: 0.9em; }\n" +
            ".md-fences { margin-bottom: 15px; margin-top: 15px; padding: 8px 1em 6px; }\n" +
            ".task-list { padding-left: 0px; }\n" +
            ".task-list-item { padding-left: 32px; }\n" +
            ".task-list-item input { top: 3px; left: 8px; }\n" +
            "@media screen and (min-width: 914px) {\n" +
            "}\n" +
            "@media print {\n" +
            "  html { font-size: 13px; }\n" +
            "  table, pre { break-inside: avoid; }\n" +
            "  pre { word-wrap: break-word; }\n" +
            "}\n" +
            ".md-fences { background-color: rgb(248, 248, 248); }\n" +
            "#write pre.md-meta-block { padding: 1rem; font-size: 85%; line-height: 1.45; background-color: rgb(247, 247, 247); border: 0px; border-radius: 3px; color: rgb(119, 119, 119); margin-top: 0px !important; }\n" +
            ".mathjax-block > .code-tooltip { bottom: 0.375rem; }\n" +
            "#write > h3.md-focus::before { left: -1.5625rem; top: 0.375rem; }\n" +
            "#write > h4.md-focus::before { left: -1.5625rem; top: 0.285714rem; }\n" +
            "#write > h5.md-focus::before { left: -1.5625rem; top: 0.285714rem; }\n" +
            "#write > h6.md-focus::before { left: -1.5625rem; top: 0.285714rem; }\n" +
            ".md-image > .md-meta { border: 1px solid rgb(221, 221, 221); border-radius: 3px; font-family: Consolas, \"Liberation Mono\", Courier, monospace; padding: 2px 4px 0px; font-size: 0.9em; color: inherit; }\n" +
            ".md-tag { color: inherit; }\n" +
            ".md-toc { margin-top: 20px; padding-bottom: 20px; }\n" +
            "#typora-quick-open { border: 1px solid rgb(221, 221, 221); background-color: rgb(248, 248, 248); }\n" +
            "#typora-quick-open-item { background-color: rgb(250, 250, 250); border-color: rgb(254, 254, 254) rgb(229, 229, 229) rgb(229, 229, 229) rgb(238, 238, 238); border-style: solid; border-width: 1px; }\n" +
            "#md-notification::before { top: 10px; }\n" +
            ".on-focus-mode blockquote { border-left-color: rgba(85, 85, 85, 0.117647); }\n" +
            "header, .context-menu, .megamenu-content, footer { font-family: \"Segoe UI\", Arial, sans-serif; }\n" +
            "\n" +
            "tree {\n" +
            " list-style-type: none;\n" +
            " background: url(images/vline.png) repeat-y;\n" +
            " margin-left:20px\n" +
            " padding: 0;\n" +
            "}\n" +
            "\n" +
            "li {\n" +
//            " list-style-type:none;\n" +
            " margin: 0;\n" +
            " padding: 0 12px;\n" +
            " line-height: 20px;\n" +
            " background: url(images/node.png) no-repeat;\n" +
            " color: #369;\n" +
            " font-weight: bold;\n" +
            "}\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "</style>\n" +
            "</head>\n" +
            "<body class='typora-export' >\n" +
            "<div  id='write'  class = 'is-node'>";
    private static final String END = "</div>\n" +
            "</body>\n" +
            "</html>";
    private StringBuilder mBuilder;
    public HtmlBuilder() {
        mBuilder = new StringBuilder();
    }

    public void appendTitleAndContent(String title, String content){
        mBuilder.append("<h1><a class='md-header-anchor '></a>");
        mBuilder.append(title);
        mBuilder.append("</h1><p>");
        mBuilder.append(content);
        mBuilder.append("</p>");
    }
    public void appendExcel(int cols, int rows, String[] colTitles, String [][]content){
        mBuilder.append("<table><thead><tr>");
        for (int i = 0; i < cols; i++) {
            mBuilder.append("<th>");
            mBuilder.append(colTitles[i]);
            mBuilder.append("</th>");
        }
        mBuilder.append("</tr></thead><tbody>");
        for (int i = 0; i < rows; i++) {
            mBuilder.append("<tr>");
            for (int j = 0; j < cols; j++) {
                mBuilder.append("<td>");
                mBuilder.append(content[i][j]);
                mBuilder.append("</td>");
            }
            mBuilder.append("</tr>");
        }
        mBuilder.append("</tbody></table>");
    }

    public void appendExcel(int cols, int rows, String [][]content){
        mBuilder.append("<table><thead><tr>");
        for (int i = 0; i < cols; i++) {
            mBuilder.append("<th>");
            mBuilder.append(content[0][i]);
            mBuilder.append("</th>");
        }
        mBuilder.append("</tr></thead><tbody>");
        for (int i = 1; i < rows; i++) {
            mBuilder.append("<tr>");
            for (int j = 0; j < cols; j++) {
                mBuilder.append("<td>");
                mBuilder.append(content[i][j]);
                mBuilder.append("</td>");
            }
            mBuilder.append("</tr>");
        }
        mBuilder.append("</tbody></table>");
    }


    public void appendHtml(String sonString){
        mBuilder.append(sonString);
    }

    public String toHtml(){
        return HEAD + mBuilder.toString() + END;
    }
}
