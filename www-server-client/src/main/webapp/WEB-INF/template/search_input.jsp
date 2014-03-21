    <%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

        <a href="index.jsp"><i>Skradnik</i></a>

        <div id="search">
        <div id="searh-line">
        <form id="main-form" action="translate">
        <div id="search-tab" onkeydown="keyControl();">
        <input id="translate-input-form" type="text" value="${param.text}"
        name="text" onkeyup="doCompletion();" autocomplete="off" onblur="//clearCheatList();">
        </div>
        <div id="search-button"><input id="translate-button" type="submit" value="перевести"></div>
        </form>
        </div>
        <div id="cheat">
        <ul id="cheat-list">
        </ul>
        </div>

        </div>

        <table>
        <tbody>
        <tr>
        <td id="auto-row" colspan="2">
        <table id="complete-table">
        </table>
        </td>
        </tr>
        </tbody>
        </table>