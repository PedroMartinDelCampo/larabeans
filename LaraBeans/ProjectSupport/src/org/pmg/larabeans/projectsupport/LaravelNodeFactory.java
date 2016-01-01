/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pmg.larabeans.projectsupport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.event.ChangeListener;
import org.netbeans.api.project.Project;
import org.netbeans.spi.project.ui.support.NodeFactory;
import org.netbeans.spi.project.ui.support.NodeList;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.nodes.Children;
import org.openide.nodes.FilterNode;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;

@NodeFactory.Registration(projectType = "org-pmg-larabeans-projectsupport", position = 10)
public class LaravelNodeFactory implements NodeFactory {

    @Override
    public NodeList<?> createNodes(Project project) {
        LaravelProject p = project.getLookup().lookup(LaravelProject.class);
        assert p != null;
        return new LaravelNodeList(p);
    }

    private class LaravelNodeList implements NodeList<Node> {

        LaravelProject project;

        public LaravelNodeList(LaravelProject project) {
            this.project = project;
        }

        @Override
        public List<Node> keys() {
            FileObject laravelFolder = project.getProjectDirectory();
            List<Node> result = new ArrayList<Node>();
            if (laravelFolder != null) {
                for (FileObject laravelFolderFile : laravelFolder.getChildren()) {
                    try {
                        result.add(DataObject.find(laravelFolderFile).getNodeDelegate());
                    } catch (DataObjectNotFoundException ex) {
                        Exceptions.printStackTrace(ex);
                    }
                }
            }
            return result;
        }

        @Override
        public Node node(Node node) {
            return new FilterNode(node);
        }

        @Override
        public void addNotify() {
        }

        @Override
        public void removeNotify() {
        }

        @Override
        public void addChangeListener(ChangeListener cl) {
        }

        @Override
        public void removeChangeListener(ChangeListener cl) {
        }
        
    }
}